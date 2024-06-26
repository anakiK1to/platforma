package screens.mainScreen.floorAllocation

import io.github.rybalkinsd.kohttp.dsl.async.httpGetAsync
import io.github.rybalkinsd.kohttp.dsl.httpPost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.PlatformApiResponse
import models.ScoreAdditionalModel
import models.SubtractRequest
import models.Violations
import okhttp3.Response
import serverAddress
import serverPort
import storage.ApplicationLocalStorage

class FloorAllocationController() {


    suspend fun distribute(): String = withContext(Dispatchers.IO) {
        val response: Response = httpPost {
            host = serverAddress
            port = serverPort
            scheme = "http"
            path = "/api/v1/platform/distribute"

            header {
                "Authorization" to "Bearer ${ApplicationLocalStorage.currentToken}"
                "Content-Type" to "application/json"
            }
        }
        response.use {
            it.body()?.string() ?: "No Response Body"
        }
    }

    suspend fun getPlatformStructure(): PlatformApiResponse? {
        val response = httpGetAsync {
            host = serverAddress
            port = serverPort
            scheme = "http"
            path = "/api/v1/platform"
            header {
                "Content-Type" to "application/json"
                "Authorization" to "Bearer ${ApplicationLocalStorage.currentToken}"
            }
        }.await()
        if (response.isSuccessful) {
            val responseBody = response.body()?.string()

            responseBody?.let {
                return Json.decodeFromString(it)
            }
        }
        throw RuntimeException("Failed to fetch dishes. HTTP status code: ${response.code()}")
    }


    suspend fun getViolations(): List<Violations>? {
        val response = httpGetAsync {
            host = serverAddress
            port = serverPort
            scheme = "http"
            path = "/api/v1/bars/violations"
            header {
                "Content-Type" to "application/json"
                "Authorization" to "Bearer ${ApplicationLocalStorage.currentToken}"
            }
        }.await()
        if (response.isSuccessful) {
            val responseBody = response.body()?.string()

            responseBody?.let {
                return Json.decodeFromString(it)
            }
        }
        throw RuntimeException("Failed to fetch dishes. HTTP status code: ${response.code()}")
    }
    
    suspend fun subtract(subtractModel: SubtractRequest) :Boolean = withContext(Dispatchers.Default) {
        val jsonBody = Json.encodeToString(subtractModel)
        val response: Response = httpPost {
            host = serverAddress
            port = serverPort
            scheme = "http"
            path = "/api/v1/bars/subtract"

            header {
                "Authorization" to "Bearer ${ApplicationLocalStorage.currentToken}"
                "Content-Type" to "application/json"
            }
            body {
                json(jsonBody)
            }
        }
        response.use {
            it.body()?.string() ?: "No Response Body"
        }
        return@withContext response.isSuccessful
    }

    suspend fun add(scoreAdditionalModel: ScoreAdditionalModel) :Boolean = withContext(Dispatchers.Default) {
        val jsonBody = Json.encodeToString(scoreAdditionalModel)
        val response: Response = httpPost {
            host = serverAddress
            port = serverPort
            scheme = "http"
            path = "/api/v1/bars/add"

            header {
                "Authorization" to "Bearer ${ApplicationLocalStorage.currentToken}"
                "Content-Type" to "application/json"
            }
            body {
                json(jsonBody)
            }
        }
        response.use {
            it.body()?.string() ?: "No Response Body"
        }
        return@withContext response.isSuccessful
    }
}
