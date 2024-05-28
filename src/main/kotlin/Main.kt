import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            primary = Color.Black,
            onPrimary = Color.White,
        )
    ) {
        LoginScreen()
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
