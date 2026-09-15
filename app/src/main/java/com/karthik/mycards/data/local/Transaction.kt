package com.karthik.mycards.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey

/**
 * A single transaction. Linked to a Card via `cardId`
 * (the id of the card it belongs to).
 */
@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cardId: Int,        // which card this transaction belongs to
    val merchant: String,   // e.g. "Starbucks"
    val category: String,   // e.g. "Dining", "Groceries", "Travel"
    val amount: Double,     // e.g. 6.75
    val date: String        // ISO date, e.g. "2026-07-14"
)
