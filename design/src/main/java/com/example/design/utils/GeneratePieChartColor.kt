package com.example.design.utils

import androidx.compose.ui.graphics.Color


fun generateColorForCategory(category: String): Color {
    return when (category) {
        "Food & Drinks" -> Color(0xFFE57373)
        "Grocery" -> Color(0xFF81C784)
        "Travel" -> Color(0xFF64B5F6)
        "Shopping" -> Color(0xFFbA68C8)
        "Entertainment" -> Color(0xFF4DB6AC)
        "Bills" -> Color(0xFFFF5722)
        else -> Color(0xFFB0BEC5)
    }
}