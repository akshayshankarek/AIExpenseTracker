package com.example.expensehome

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.expensehome.ui.AddExpenseScreen
import com.example.expensehome.ui.ExpenseListScreen
import com.example.navigation.NavRoutes


fun NavGraphBuilder.expenseHomeGraph(navHostController: NavHostController) {
    composable(
        route = NavRoutes.ExpenseList.route
    ) {
        ExpenseListScreen(
            onAddClick = { navHostController.navigateToAddExpense() }
        )
    }

    composable(
        route = NavRoutes.AddExpense.route
    ) {
        AddExpenseScreen()
    }
}

fun NavController.navigateToAddExpense() {
    navigate(NavRoutes.AddExpense.route)
}