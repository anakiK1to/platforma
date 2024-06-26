package screens.mainScreen.menuConfiguration

import io.github.rybalkinsd.kohttp.dsl.async.httpGetAsync
import io.github.rybalkinsd.kohttp.dsl.httpPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.AddDishModel
import models.ProductApiResponse
import okhttp3.Response
import serverAddress
import serverPort
import storage.ApplicationLocalStorage

class MenuConfigurationController() {
    suspend fun getProducts(): ProductApiResponse? {
        val response = httpGetAsync {
            host = serverAddress
            port = serverPort
            scheme = "http"
            path = "/api/v1/products"
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

    suspend fun addDish(addDishModel: AddDishModel): String = withContext(Dispatchers.IO) {
        val jsonBody = Json.encodeToString(addDishModel)

        val response: Response = httpPost {
            host = serverAddress
            port = serverPort
            scheme = "http"
            path = "/api/v1/dishes"

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
    }
}