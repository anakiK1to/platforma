package models

import kotlinx.serialization.Serializable
@Serializable
data class Product(
    val id: String,
    val name: String,
    val calories: String,
    val proteins: Int,
    val fats: Int,
    val carbohydrates: Int,
    val weight: Int
) {
    fun toIngredient(amount: Int = 1): Ingredient = Ingredient(
        amount = amount,
        productId = id
    )
}

@Serializable
data class AddDishModel(
    val name: String,
    val description: String,
    val receipt: String,
    val ingredients: List<Ingredient>
)

@Serializable
data class Ingredient(
    val amount: Int,
    val productId: String,
)

@Serializable
data class FavouriteDishModel(
    val id: String,
    val name: String,
)

@Serializable
data class ServDish(
    val id: String,
    val name: String,
    val description: String,
    val receipt: String,
    val ingredients: List<ServIngredient>
)

@Serializable
data class ServIngredient(
    val amount: Int,
    val product: ServProduct
)

@Serializable
data class ServProduct(
    val id: String,
    val name: String,
    val calories: String,
    val proteins: Int,
    val fats: Int,
    val carbohydrates: Int,
    val weight: Int
)


