package com.goally.task.data.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.HttpResponseValidator
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import javax.inject.Inject

class KtorClient @Inject constructor() {

    val ktorClient: HttpClient by lazy {
        HttpClient(Android) {
            install(DefaultRequest) {
                headers.append("Content-Type", "application/json")
            }
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
            defaultRequest {

                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)

                install(HttpTimeout) {
                    socketTimeoutMillis = 30000
                    requestTimeoutMillis = 30000
                    connectTimeoutMillis = 30000
                }
                HttpResponseValidator {
                    validateResponse { response ->
                        if (response.status.value in 200..299) {
                            Log.e("Success", "good to go")
                        } else {
                            if (!response.status.isSuccess()) {
                                throw IllegalStateException("Unexpected status code: ${response.status}")
                            }
                            if (response.headers.contains("X-Custom-Header")) {
                                val customHeaderValue = response.headers["X-Custom-Header"]
                                if (customHeaderValue != "expected-value") {
                                    throw IllegalStateException("Invalid X-Custom-Header value: $customHeaderValue")
                                }
                            } else {
                                throw IllegalStateException("Missing X-Custom-Header")
                            }
                        }

                    }
                }
            }
        }
    }
}
