package com.karthik.mycards.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase

/**
 * The Room database. Lists the tables (entities) and exposes the DAOs.
 * Room generates the real working implementation from this abstract class.
 */
@Database(
    entities = [Card::class, Transaction::class],
    version = 1,
    exportSchema = false
)
abstract class MyCardsDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
    abstract fun transactionDao(): TransactionDao
}
