package com.karthik.mycards.data.remote

/**
 * Data shapes exchanged with the AI proxy. Gson maps these
 * property names 1:1 to the JSON the proxy expects/returns.
 */
data class TransactionDto(
    val merchant: String,
    val category: String,
    val amount: Double,
    val date: String
)

data class ChatRequest(
    val question: String,
    val transactions: List<TransactionDto>
)

data class ChatResponse(
    val answer: String
)
