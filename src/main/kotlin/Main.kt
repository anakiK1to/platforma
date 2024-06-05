import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import components.Indicator
import kotlinx.coroutines.delay
import navigation.NavHostComponent
import java.io.File


fun main() = application {
    val lifecycle = LifecycleRegistry()
    val root = NavHostComponent(DefaultComponentContext(lifecycle))

    val rootFile = File(".Platforma")

    with(rootFile) {
        if (!isDirectory)
            mkdir()
    }
    var isSplashScreenShowing by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(400)
        isSplashScreenShowing = false
    }
    Window(
        state = WindowState(
            placement = WindowPlacement.Maximized
        ),
        title = "Платформа $applicationVersion",
        onCloseRequest = { exitApplication() }
    ) {
        MaterialTheme(
            colors = MaterialTheme.colors.copy(
                primary = Color.Black,
                onPrimary = Color.White,
                onBackground = Color.Black
            ),
            typography = Typography(
                defaultFontFamily = FontFamily(
                    Font(
                        resource = "Roboto-Medium.ttf",
                        weight = FontWeight.W400,
                        style = FontStyle.Normal
                    )
                )
            )
        ) {
            if (isSplashScreenShowing) {
                Box(
                    Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        Modifier
                            .background(Color(217, 229, 232))
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Indicator(
                            onCancelClick = {
                            }
                        )
                    }
                }
            } else {
                root.render()
            }
        }
    }
}

const val applicationVersion = "0.0.1"
