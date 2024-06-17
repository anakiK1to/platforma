import kotlinx.serialization.Serializable

const val serverAddress = "localhost"
const val serverPort = 8080


@Serializable
data class ResponseData(
    val message: String
)


@Serializable
data class AuthResponseData(
    val token: String
)