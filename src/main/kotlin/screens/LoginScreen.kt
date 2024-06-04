package screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.ComponentContext
import navigation.Component
import kotlin.reflect.KFunction1


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
    var login by remember { mutableStateOf("") }
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

            Spacer(Modifier.height(20.dp).fillMaxWidth())

            Button(
                onClick = {
                    //todo get successful auth
                    onGoClicked(println("auth completed"))
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