package screens.loginScreen

import AuthResponseData
import io.github.rybalkinsd.kohttp.dsl.async.httpPostAsync
import io.github.rybalkinsd.kohttp.ext.asString
import kodeinDi.DiContainer
import kotlinx.serialization.*
import serverAddress
import serverPort
import storage.ApplicationLocalStorage


class LoginScreenController(diContainer: DiContainer) {

    private val loginEndpoint = "/api/v1/authenticate"
    suspend fun sendAuthRequest(
        login: String,
        password: String,
        applicationLocalStorage: ApplicationLocalStorage
    ): String? {
        try {
            val response = httpPostAsync {
                host = serverAddress
                port = serverPort
                scheme = "http"
                path = loginEndpoint
                header {
                    "Content-Type" to "application/json"
                }
                body {
                    json {
                        "username" to login
                        "password" to password
                    }
                }
            }.await()
            return when (response.code()) {

                200 -> {
                    applicationLocalStorage.currentToken =
                        kotlinx.serialization.json.Json.decodeFromString<AuthResponseData>(response.asString()!!).token
                    println(response.message())
                    null
                }

                else ->
                    response.message()
            }
        } catch (timeout: java.net.SocketTimeoutException) {
            return "auth bad request" + timeout.localizedMessage
        }
    }
}