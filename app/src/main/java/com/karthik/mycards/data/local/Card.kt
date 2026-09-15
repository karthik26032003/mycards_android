package com.karthik.mycards.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey

/**
 * A payment card. @Entity tells Room this class is a table ("cards").
 * Each property becomes a column; `id` is the primary key.
 */
@Entity(tableName = "cards")
data class Card(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nickname: String,   // e.g. "Everyday Rewards"
    val last4: String,      // last 4 digits, e.g. "4821"
    val network: String,    // e.g. "Visa" / "Mastercard"
    val type: String        // "Credit" or "Debit"
)
