package com.example.expensehome.expenselist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.data.model.Expense
import com.example.data.utils.normalizeCategory
import com.example.design.theme.Purple40
import com.example.design.utils.generateColorForCategory
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import me.bytebeats.views.charts.pie.render.SimpleSliceDrawer
import me.bytebeats.views.charts.simpleChartAnimation

@Composable
internal fun ExpensePieChart(expenses: List<Expense>) {
    val categoryTotals = calculateCategoryTotals(expenses)
    val pieChartData = PieChartData(
        slices = categoryTotals.map { (category, amount) ->
            PieChartData.Slice(value = amount.toFloat(), color = generateColorForCategory(category))
        }
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PieChart(
            pieChartData = pieChartData,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(16.dp),
            animation = simpleChartAnimation(),
            sliceDrawer = SimpleSliceDrawer(sliceThickness = 40f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Adds space between each row
        ) {
            categoryTotals.forEach { (category, amount) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // This Row contains the color dot and category name
                    Row(
                        modifier = Modifier.weight(1f), // Takes up all available space
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(
                                    color = generateColorForCategory(category),
                                    shape = CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = category,
                            style = MaterialTheme.typography.body1,
                            color = Purple40
                        )
                    }

                    // The amount is in its own Text composable, aligned to the end
                    Text(
                        text = "₹%.2f".format(amount),
                        style = MaterialTheme.typography.body1,
                        color = Purple40,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }

}

fun calculateCategoryTotals(expenses: List<Expense>): List<Pair<String, Double>> {
    return expenses.groupBy {
        normalizeCategory(it.category)
    }.map { (category, items) ->
        val total = items.sumOf { it.amount ?: 0.0 }
        category to total
    }.sortedByDescending { it.second }
}
