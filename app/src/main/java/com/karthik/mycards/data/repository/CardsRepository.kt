package com.karthik.mycards.data.repository

import com.karthik.mycards.data.MockData
import com.karthik.mycards.data.local.Card
import com.karthik.mycards.data.local.CardDao
import com.karthik.mycards.data.local.Transaction
import com.karthik.mycards.data.local.TransactionDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single source of truth for cards & transactions.
 * The rest of the app talks ONLY to this class — never to the DAOs/DB directly.
 * Today it reads from Room; tomorrow it could read from a network API,
 * and no screen would need to change.
 */
@Singleton
class CardsRepository @Inject constructor(
    private val cardDao: CardDao,
    private val transactionDao: TransactionDao
) {
    fun getAllCards(): Flow<List<Card>> = cardDao.getAllCards()

    fun getCard(cardId: Int): Flow<Card?> = cardDao.getCard(cardId)

    fun getTransactionsForCard(cardId: Int): Flow<List<Transaction>> =
        transactionDao.getTransactionsForCard(cardId)

    fun getTransaction(transactionId: Int): Flow<Transaction?> =
        transactionDao.getTransaction(transactionId)

    // Used later to give the AI assistant the full transaction context.
    suspend fun getAllTransactionsOnce(): List<Transaction> =
        transactionDao.getAllTransactionsOnce()

    // Loads the sample data the first time the app runs (empty DB only).
    suspend fun seedIfEmpty() {
        if (cardDao.count() == 0) {
            cardDao.insertAll(MockData.cards)
            transactionDao.insertAll(MockData.transactions)
        }
    }
}
