package screens.mainScreen.registrationScreen

import io.github.rybalkinsd.kohttp.dsl.async.httpGetAsync
import io.github.rybalkinsd.kohttp.dsl.httpPost
import kodeinDi.DiContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.DishApiResponse
import models.RegisterPrisonerModel
import models.PrisonerApiResponse
import okhttp3.Response
import serverAddress
import serverPort
import storage.ApplicationLocalStorage

class RegistrationController(diContainer: DiContainer) {
    private val applicationLocalStorage = ApplicationLocalStorage
    private val prisonersEndpoint = "/api/v1/prisoners"

    suspend fun getPrisoners(): PrisonerApiResponse? {
            val response = httpGetAsync {
                host = serverAddress
                port = serverPort
                scheme = "http"
                path = prisonersEndpoint
                header {
                    "Content-Type" to "application/json"
                    "Authorization" to "Bearer ${applicationLocalStorage.currentToken}"
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

    suspend fun getDishes(): DishApiResponse? {
        val response = httpGetAsync {
            host = serverAddress
            port = serverPort
            scheme = "http"
            path = "/api/v1/dishes"
            header {
                "Content-Type" to "application/json"
                "Authorization" to "Bearer ${applicationLocalStorage.currentToken}"
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


    suspend fun registerPrisoner(prisonerModel: RegisterPrisonerModel) : String = withContext(Dispatchers.IO) {
        val jsonBody = Json.encodeToString(prisonerModel)
        val response: Response = httpPost {
            host = serverAddress
            port = serverPort
            scheme = "http"
            path = "/api/v1/prisoners"

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
