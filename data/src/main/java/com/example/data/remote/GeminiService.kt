package com.example.data.remote

import com.example.data.BuildConfig
import com.example.data.remote.model.ExpenseExtractionResult
import com.example.data.remote.model.GeminiContent
import com.example.data.remote.model.GeminiPart
import com.example.data.remote.model.GeminiRequest
import com.example.data.remote.model.GeminiResponse
import com.example.data.remote.model.PromptType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import org.json.JSONObject

class GeminiService() {
    private val client = HttpClient(CIO) {
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 15000
            socketTimeoutMillis = 30000
        }
        install(ContentNegotiation) {
            json(
                kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
    }

    suspend fun suggestionRequest(title: String, promptType: PromptType): String {
        return try {
            val response = client.post(
                "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent"
            ) {
                contentType(ContentType.Application.Json)
                parameter("key", BuildConfig.GEMINI_API_KEY)
                setBody(
                    GeminiRequest(
                        contents = getContentBasedOnPrompts(title, promptType)
                    )
                )
            }
            if (response.status.value != 200) {
                val error = response.bodyAsText()
                println("Gemini api error $error")
            }

            val geminiResponse = response.body<GeminiResponse>()
            println("Gemini response$geminiResponse")
            geminiResponse.candidates
                .firstOrNull()
                ?.content
                ?.parts
                ?.firstOrNull()
                ?.text
                ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    suspend fun extractExpenseDetails(receiptText: String): ExpenseExtractionResult {

        val response = suggestionRequest(receiptText, PromptType.EXTRACT_EXPENSE_DETAILS)
        return parseGeminiExtractExpenseJson(response)
    }

    private fun parseGeminiExtractExpenseJson(response: String): ExpenseExtractionResult {
        return try {
            val jsonStart = response.indexOf("{")
            val jsonEnd = response.lastIndexOf("}")
            if(jsonStart == -1 || jsonEnd == -1){
                return ExpenseExtractionResult()
            }
            val jsonString = response.substring(jsonStart, jsonEnd+1)
            val json = JSONObject(jsonString)
            ExpenseExtractionResult(
                title = json.optString("merchant"),
                amount = json.optString("amount"),
                date = json.optString("date"),
                category = json.optString("category")
            )
        } catch (e: Exception) {
            e.printStackTrace()
            ExpenseExtractionResult()
        }
    }
}