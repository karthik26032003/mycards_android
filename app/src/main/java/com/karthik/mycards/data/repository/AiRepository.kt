package com.karthik.mycards.data.repository

import com.karthik.mycards.data.remote.ChatApi
import com.karthik.mycards.data.remote.ChatRequest
import com.karthik.mycards.data.remote.TransactionDto
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Bridges the app's data to the AI proxy: it pulls the user's transactions
 * from the local DB (via CardsRepository) and sends them with the question.
 */
@Singleton
class AiRepository @Inject constructor(
    private val chatApi: ChatApi,
    private val cardsRepository: CardsRepository
) {
    suspend fun ask(question: String): String {
        val transactions = cardsRepository.getAllTransactionsOnce().map {
            TransactionDto(
                merchant = it.merchant,
                category = it.category,
                amount = it.amount,
                date = it.date
            )
        }
        val response = chatApi.chat(
            ChatRequest(question = question, transactions = transactions)
        )
        return response.answer
    }
}
