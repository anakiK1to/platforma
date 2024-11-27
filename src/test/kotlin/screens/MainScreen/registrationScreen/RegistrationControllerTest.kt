import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import kodeinDi.DiContainer
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import models.*
import org.junit.Rule
import org.junit.Test
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.compose.withDI
import org.kodein.di.singleton
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import screens.mainScreen.registrationScreen.FavouriteDishSelectionDropdownMenu
import screens.mainScreen.registrationScreen.RegistrationController
import screens.mainScreen.registrationScreen.RegistrationScreen
import kotlin.test.assertEquals

class RegistrationScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun createMockDish(): ServDish {
        val mockProduct = ServProduct(
            id = "1",
            name = "Пельмени",
            calories = "200",
            proteins = 10,
            fats = 5,
            carbohydrates = 30,
            weight = 100
        )

        val mockIngredient = ServIngredient(
            amount = 2,
            product = mockProduct
        )

        return ServDish(
            id = "1",
            name = "Пельмени",
            description = "Очень вкусные пельмени",
            receipt = "Просто варите",
            ingredients = listOf(mockIngredient)
        )
    }

    @Test
    fun testRegistrationScreenFieldsAreVisible() = runTest {
        val registrationController = mock<RegistrationController>()
        val mockDish = createMockDish()
        whenever(registrationController.getDishes()).thenReturn((
            DishApiResponse(
                content = listOf(mockDish),
                totalCount = 10,
                page = 1,
                size = 10
            )
        ))


        val di = DI {
            bind<RegistrationController>() with singleton { registrationController }
        }

        composeTestRule.setContent {
            withDI(di) {
                RegistrationScreen()
            }
        }


        composeTestRule.onNodeWithText("Фамилия").assertIsDisplayed()
        composeTestRule.onNodeWithText("Имя").assertIsDisplayed()
        composeTestRule.onNodeWithText("Отчество").assertIsDisplayed()
        composeTestRule.onNodeWithText("Паспорт").assertIsDisplayed()
        composeTestRule.onNodeWithText("Дата рождения").assertIsDisplayed()
        composeTestRule.onNodeWithText("Рост").assertIsDisplayed()
        composeTestRule.onNodeWithText("Вес").assertIsDisplayed()
        composeTestRule.onNodeWithText("Подтвердить регистрацию").assertIsDisplayed()
    }

    @Test
    fun testButtonEnabledWhenAllFieldsAreValid() {

        composeTestRule.setContent {
            RegistrationScreen()
        }


        composeTestRule.onNodeWithText("Фамилия").performTextInput("Иванов")
        composeTestRule.onNodeWithText("Имя").performTextInput("Иван")
        composeTestRule.onNodeWithText("Отчество").performTextInput("Иванович")
        composeTestRule.onNodeWithText("Паспорт").performTextInput("1234567890")
        composeTestRule.onNodeWithText("Дата рождения").performTextInput("01.01.1980")
        composeTestRule.onNodeWithText("Рост").performTextInput("180")
        composeTestRule.onNodeWithText("Вес").performTextInput("75")


        composeTestRule.onNodeWithText("Подтвердить регистрацию").assertIsEnabled()
    }

    @Test
    fun testDishSelection() = runTest {

        val mockDish = createMockDish()


        val registrationController = mock<RegistrationController>()
        whenever(registrationController.getDishes()).thenReturn(DishApiResponse(
            content = listOf(mockDish),
            totalCount = 1,
            page = 1,
            size = 10
        ))

        composeTestRule.setContent {
            RegistrationScreen()
        }


        composeTestRule.onNodeWithText(mockDish.name).assertIsDisplayed()


        composeTestRule.onNodeWithText(mockDish.name).performClick()


        composeTestRule.onNodeWithText(mockDish.name).assertIsDisplayed()
    }

    @Test
    fun testSubmitButtonFunctionality() = runTest {

        val registrationController = mock<RegistrationController>()

        composeTestRule.setContent {
            RegistrationScreen()
        }


        composeTestRule.onNodeWithText("Фамилия").performTextInput("Иванов")
        composeTestRule.onNodeWithText("Имя").performTextInput("Иван")
        composeTestRule.onNodeWithText("Отчество").performTextInput("Иванович")
        composeTestRule.onNodeWithText("Паспорт").performTextInput("1234567890")
        composeTestRule.onNodeWithText("Дата рождения").performTextInput("01.01.1980")
        composeTestRule.onNodeWithText("Рост").performTextInput("180")
        composeTestRule.onNodeWithText("Вес").performTextInput("75")


        val mockDish = createMockDish()
        composeTestRule.onNodeWithText(mockDish.name).performClick()


        composeTestRule.onNodeWithText("Подтвердить регистрацию").performClick()


        verify(registrationController).registerPrisoner(
            RegisterPrisonerModel(
                lastName = "",
                firstName = "",
                patronymic = "",
                passport = "",
                weight = 1,
                birthDate = "String",
                favoriteDish = " String"
            )
        )
    }
}

class FavouriteDishSelectionDropdownMenuTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun favouriteDishSelectionDropdownMenuTest() = runTest {
        var selectedDish: ServDish? = null
        val testDishes = listOf(
            ServDish(
                id = "1",
                name = "Pizza",
                description = "Delicious pizza",
                receipt = "Pizza recipe",
                ingredients = emptyList()
            )
        )


        composeTestRule.setContent {
            FavouriteDishSelectionDropdownMenu(
                availableDishes = testDishes,
                selectedDish = selectedDish,
                onSelectDish = { selectedDish = it }
            )
        }

        composeTestRule.onNodeWithText("Выберите блюдо").performClick()
        composeTestRule.onNodeWithText("Pizza").performClick()

        assertEquals("Pizza", selectedDish?.name)
    }
}
