package com.example.data.remote

import com.example.data.BuildConfig
import com.example.data.remote.model.GeminiContent
import com.example.data.remote.model.GeminiPart
import com.example.data.remote.model.GeminiRequest
import com.example.data.remote.model.GeminiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

class GeminiService() {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                kotlinx.serialization.json.Json{
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
    }

    suspend fun suggestCategory(title: String): String {
        return try {
            val response = client.post(
                "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent"
            ) {
                contentType(ContentType.Application.Json)
                parameter("key", BuildConfig.GEMINI_API_KEY)
                setBody(
                    GeminiRequest(
                        contents = listOf(
                            GeminiContent(
                                parts = listOf(
                                    GeminiPart("Suggest one spending category for : \"$title\"")
                                )
                            )
                        )
                    )
                )
            }
            if(response.status.value != 200) {
                val error = response.bodyAsText()
                println("Gemini api error $error")
            }

            val geminiResponse = response.body<GeminiResponse>()
            geminiResponse.candidates
                .firstOrNull()
                ?.content
                ?.parts
                ?.firstOrNull()
                ?.text
                ?: "Other"
        }
        catch (e: Exception) {
            e.printStackTrace()
            "Other"
        }
    }
}