package com.karthik.mycards.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit turns this interface into a working HTTP client.
 * @POST("chat") = POST {BASE_URL}chat  (i.e. http://10.0.2.2:8000/chat)
 */
interface ChatApi {
    @POST("chat")
    suspend fun chat(@Body request: ChatRequest): ChatResponse
}
