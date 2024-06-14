package navigation

import screens.loginScreen.LoginScreenComponent
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.parcelable.Parcelable
import screens.mainScreen.MainScreenComponent


/**
 * Navigator
 */
class NavHostComponent(
    componentContext: ComponentContext
) : Component, ComponentContext by componentContext {
    private val navigation = StackNavigation<ScreenConfig>()
    private val stack = childStack(
        source = navigation,
        initialConfiguration = ScreenConfig.LoginScreen,
        childFactory = ::createScreenComponent
    )

    /**
     * Factory function to create screen from given ScreenConfig
     */
    private fun createScreenComponent(
        screenConfig: ScreenConfig,
        componentContext: ComponentContext
    ): Component {
        return when (screenConfig) {
            is ScreenConfig.LoginScreen -> LoginScreenComponent(
                componentContext,
                ::onGoClicked
            )

            is ScreenConfig.MainScreen -> MainScreenComponent(
                componentContext,
                onGoBackClicked = {

                }
            )
        }
    }


    /**
     * Invoked when `GO` button clicked (InputScreen)
     */
    private fun onGoClicked(unit: Unit) {
        navigation.push(ScreenConfig.MainScreen)
    }


    /**
     * Invoked when `GO BACK` button clicked (GreetingScreen)
     */
    private fun disconnect(action: Unit) {
        navigation.pop()
    }


    /**
     * Renders screen as per request
     */
    @Composable
    override fun render() {
        Children(
            stack = stack,
            animation = stackAnimation(fade() + scale()),
        ) {
            it.instance.render()
        }
    }

    sealed class ScreenConfig : Parcelable {
        object LoginScreen : ScreenConfig()
        object MainScreen : ScreenConfig()
    }
}
