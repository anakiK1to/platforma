package screens.mainScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.Indicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen() {
    var login by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var patronymic by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("0") }
    var weight by remember { mutableStateOf("0") }
    var showIndicator by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(Modifier.width(450.dp)) {
            Row(Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = "Регистрация нового заключенного",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                )
            }
            Row(Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = "Заполните все поля для регистрации",
                    fontSize = 16.sp,
                    textAlign = TextAlign.End,
                )
            }
            Spacer(Modifier.height(60.dp).fillMaxWidth())
            OutlinedTextField(
                value = surname,
                onValueChange = {
                    surname = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                label = {
                    Text("Фамилия", color = Color.LightGray)
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(20.dp).fillMaxWidth())
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                label = {
                    Text("Имя", color = Color.LightGray)
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(20.dp).fillMaxWidth())
            OutlinedTextField(
                value = patronymic,
                onValueChange = {
                    login = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                label = {
                    Text("Отчество", color = Color.LightGray)
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(20.dp).fillMaxWidth())
            Row {
                OutlinedTextField(
                    value = height,
                    onValueChange = {
                        height = it
                    },
                    textStyle = TextStyle(
                        fontSize = 16.sp
                    ),
                    label = {
                        Text("login", color = Color.LightGray)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(20.dp))
                OutlinedTextField(
                    value = weight,
                    onValueChange = {
                        weight = it
                    },
                    textStyle = TextStyle(
                        fontSize = 16.sp
                    ),
                    label = {
                        Text("login", color = Color.LightGray)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(60.dp).fillMaxWidth())

            Button(
                onClick = {
                    //TODO provide registration
                    scope.launch {
                        showIndicator = true
                        delay(1000)
                        showIndicator = false
                    }
                },
                content = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        if (showIndicator)
                            Indicator(
                                size = 36.dp,
                                onCancelClick = {}
                            )
                        else
                            Text(
                                text = "Подтвердить регистрацию",
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