package screens.loginScreen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch
import navigation.Component
import org.kodein.di.compose.localDI
import org.kodein.di.instance


class LoginScreenComponent(
    private val componentContext: ComponentContext,
    private val onGoClicked: (Unit) -> Unit,
) : Component, ComponentContext by componentContext {

    @Composable
    override fun render() {
        LoginScreen(
            onGoClicked
        )
    }
}

@Preview
@Composable
fun LoginScreen(onGoClicked: (Unit) -> Unit) {
    val loginScreenController: LoginScreenController by localDI().di.instance()
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center,
    ) {
        Column(modifier = Modifier.width(450.dp)) {
            Row(Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = "Вход в аккаунт",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                )
            }
            Row(Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = "Введите ваш пароль для входа в устройство",
                    fontSize = 16.sp,
                    textAlign = TextAlign.End,
                )
            }
            Spacer(Modifier.height(40.dp).fillMaxWidth())

            OutlinedTextField(
                value = login,
                onValueChange = {
                    login = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                label = {
                    Text("Логин", color = Color.LightGray)
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                label = {
                    Text("Пароль", color = Color.LightGray)
                },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val (icon, iconColor: Color) = if (showPassword) {
                        Pair(
                            Icons.Default.VisibilityOff,
                            contentColorFor(Color.Cyan)
                        )
                    } else {
                        Pair(
                            Icons.Default.Visibility,
                            contentColorFor(Color.Cyan)
                        )
                    }
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = icon,
                            contentDescription = "Visibility",
                            tint = iconColor
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp).fillMaxWidth())

            Button(
                onClick = {
                    scope.launch {
                        val authResponse = loginScreenController.sendAuthRequest(login, password)
                        if (authResponse == null) {
                            onGoClicked(println("auth completed"))
                        } else {
                            println(authResponse)
                        }
                    }
                },
                content = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Войти с помощью логина",
                            textAlign = TextAlign.Center
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .height(60.dp)
                    .pointerHoverIcon(PointerIcon.Hand)
            )
        }
    }
}