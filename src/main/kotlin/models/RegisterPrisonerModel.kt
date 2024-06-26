package models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterPrisonerModel(
    val lastName: String,
    val firstName: String,
    val patronymic: String,
    val passport: String,
    val weight: Int,
    val birthDate: String,
    val favoriteDish: String
)

@Serializable
data class GetPrisonersModel(
    val id: String,
    val lastName: String,
    val firstName: String,
    val patronymic: String,
    val weight: Float,
    val birthDate: String,
    val favoriteDish: FavouriteDishModel,
    val rating: Int
)
