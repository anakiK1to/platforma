package screens.mainScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import components.Indicator
import kotlinx.coroutines.delay
import navigation.Component
import screens.mainScreen.floorAllocation.FloorAllocation
import screens.mainScreen.menuConfiguration.MenuConfiguration
import screens.mainScreen.registrationScreen.RegistrationScreen
import storage.ApplicationLocalStorage

class MainScreenComponent(
    private val componentContext: ComponentContext,
    private val onGoBackClicked: () -> Unit
) : Component, ComponentContext by componentContext {

    @Composable
    override fun render() {
        val applicationLocalStorage = ApplicationLocalStorage
        MainScreen(onGoBackClicked, applicationLocalStorage)
    }
}

@Composable
fun MainScreen(onGoBackClicked: () -> Unit, applicationLocalStorage: ApplicationLocalStorage) {
    var uiInitializationCompleted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        uiInitializationCompleted = false
        delay(600)
        uiInitializationCompleted = true
        println(applicationLocalStorage.currentToken)
    }

    if (!uiInitializationCompleted) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Indicator(
                onCancelClick = {
                    onGoBackClicked()
                }
            )
        }
    } else {
        mainScreen()
    }
}


@Composable
fun mainScreen() {
    var selectedScreen by remember { mutableStateOf(MainScreenNavigation.REGISTRATION) }
    Column {
        Row {
            NavigationPanel(
                onClick = {
                    selectedScreen = it
                }
            )
            when (selectedScreen) {
                MainScreenNavigation.REGISTRATION -> {
                    RegistrationScreen()
                }
                MainScreenNavigation.FLOOR_ALLOCATION -> {
                    FloorAllocation()
                }
                MainScreenNavigation.MENU_CONFIGURATION -> {
                    MenuConfiguration()
                }
                else -> {
                }
            }
        }
    }
}

