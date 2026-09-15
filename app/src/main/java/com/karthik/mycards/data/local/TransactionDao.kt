package com.karthik.mycards.data.local

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for transactions.
 */
@Dao
interface TransactionDao {

    // Transactions for one card, newest first.
    @Query("SELECT * FROM transactions WHERE cardId = :cardId ORDER BY date DESC")
    fun getTransactionsForCard(cardId: Int): Flow<List<Transaction>>

    // A single transaction by id (for the detail screen).
    @Query("SELECT * FROM transactions WHERE id = :transactionId")
    fun getTransaction(transactionId: Int): Flow<Transaction?>

    // Everything, once — used later to hand context to the AI assistant.
    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactionsOnce(): List<Transaction>

    @Insert
    suspend fun insertAll(transactions: List<Transaction>)
}
