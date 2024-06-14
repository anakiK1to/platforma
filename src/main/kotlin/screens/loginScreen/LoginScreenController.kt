package screens.loginScreen

import ResponseData
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.rybalkinsd.kohttp.dsl.async.httpPostAsync
import io.github.rybalkinsd.kohttp.ext.asString
import io.github.rybalkinsd.kohttp.util.Json
import kodeinDi.DiContainer
import kotlinx.serialization.*
import serverAddress
import serverPort


class LoginScreenController(diContainer: DiContainer) {
    private val loginEndpoint = "/api/v1/authorize"

    suspend fun sendAuthRequest(login: String, password: String): String? {
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
                    kotlinx.serialization.json.Json.decodeFromString<ResponseData>(response.asString()!!)
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