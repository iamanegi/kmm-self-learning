package com.amannegi.kmmdemo

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*

class KtorHelper {

    private val apiUrl = "https://dummyjson.com/products"

    private val client = HttpClient() {

        // for kotlinx serialize/deserialize
        install(ContentNegotiation) {
            json()
        }

        // api timeout
        install(HttpTimeout) {
            requestTimeoutMillis = 1000
        }
    }

    suspend fun getProducts(): ProductsResponse? {
        return try {
            client.get(apiUrl).body()
        } catch (e: Exception) {
            null
        }
    }

}