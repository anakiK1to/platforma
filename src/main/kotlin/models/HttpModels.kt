package models

import kotlinx.serialization.Serializable

@Serializable
data class PrisonerApiResponse(
    val content: List<GetPrisonersModel>,
    val totalCount: Int,
    val page: Int,
    val size: Int
)

@Serializable
data class DishApiResponse(
    val content: List<ServDish>,
    val totalCount: Int,
    val page: Int,
    val size: Int
)



@Serializable
data class ProductApiResponse(
    val content: List<Product>,
    val totalCount: Int,
    val page: Int,
    val size: Int
)

@Serializable
data class PlatformApiResponse(
    val content: List<PlatformStructure>,
    val totalCount: Int,
    val page: Int,
    val size: Int
)

@Serializable
data class PlatformStructure(
    val floor: Int,
    val firstPrisoner: ServerPrisonerModel,
    val secondPrisoner: ServerPrisonerModel,
)

@Serializable
data class ServerPrisonerModel(
    val id: String,
    val lastName: String,
    val firstName: String,
    val patronymic: String,
    val rating: Float
)

@Serializable
data class Violations(
    val code: String,
    val name: String,
    val score: Float,
)

@Serializable
data class SubtractRequest(
    val personId: String,
    val violationCode: String
)

@Serializable
data class ScoreAdditionalModel(
    val personId: String,
    val score: Float,
)