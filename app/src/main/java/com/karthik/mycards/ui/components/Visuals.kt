package com.karthik.mycards.ui.components

import androidx.compose.ui.graphics.Color

/** Gradient color pairs for the credit-card faces. */
private val cardGradients = listOf(
    listOf(Color(0xFF1E3A8A), Color(0xFF3B82F6)), // blue
    listOf(Color(0xFF6D28D9), Color(0xFFA855F7)), // purple
    listOf(Color(0xFF0F766E), Color(0xFF14B8A6)), // teal
    listOf(Color(0xFFB91C1C), Color(0xFFF97316)), // red → orange
    listOf(Color(0xFF111827), Color(0xFF374151)), // charcoal
)

fun gradientFor(index: Int): List<Color> = cardGradients[index % cardGradients.size]

/** A small emoji per transaction category (no icon library needed). */
fun categoryEmoji(category: String): String = when (category.lowercase()) {
    "dining" -> "🍽️"       // 🍽️
    "groceries" -> "🛒"          // 🛒
    "travel" -> "✈️"             // ✈️
    "fuel" -> "⛽"                     // ⛽
    "shopping" -> "🛍️"     // 🛍️
    "subscriptions" -> "🔁"      // 🔁
    "bills" -> "🧾"              // 🧾
    "health" -> "💪"             // 💪
    else -> "💳"                 // 💳
}

/** A tint per category, used behind the emoji avatar. */
fun categoryColor(category: String): Color = when (category.lowercase()) {
    "dining" -> Color(0xFFEF4444)
    "groceries" -> Color(0xFF22C55E)
    "travel" -> Color(0xFF3B82F6)
    "fuel" -> Color(0xFFF59E0B)
    "shopping" -> Color(0xFFA855F7)
    "subscriptions" -> Color(0xFF06B6D4)
    "bills" -> Color(0xFF64748B)
    "health" -> Color(0xFF10B981)
    else -> Color(0xFF6B7280)
}
