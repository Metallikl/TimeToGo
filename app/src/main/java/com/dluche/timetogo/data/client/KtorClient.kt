package com.dluche.timetogo.data.client

import android.content.Context
import android.os.Build
import com.dluche.timetogo.data.client.ClientConsts.OPEN_STREET_BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {

    fun openStreetMapClient(context: Context) = HttpClient {
        expectSuccess = true

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(UserAgent) {
            agent = "${context.packageName}"
        }

        defaultRequest {
            url(OPEN_STREET_BASE_URL)
            contentType(ContentType.Application.Json)
        }
    }
}