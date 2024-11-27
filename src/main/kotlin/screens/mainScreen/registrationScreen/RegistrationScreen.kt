package screens.mainScreen.registrationScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.Indicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import models.GetPrisonersModel
import models.RegisterPrisonerModel
import models.ServDish
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Composable
fun FavouriteDishSelectionDropdownMenu(
    availableDishes: List<ServDish>,
    selectedDish: ServDish?,
    onSelectDish: (ServDish) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val buttonContent by derivedStateOf {
        selectedDish?.name ?: "Выберите блюдо"
    }
    Box(Modifier.testTag("FavouriteDishDropdown")) {
        Button(
            onClick = { expanded = true },
            content = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = buttonContent,
                        textAlign = TextAlign.Center
                    )
                }
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .height(60.dp)
                .pointerHoverIcon(PointerIcon.Hand)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            availableDishes.forEach { dish ->
                DropdownMenuItem(onClick = {
                    onSelectDish(dish)
                    expanded = false
                }) {
                    Text(dish.name)
                }
            }
        }
    }
}

@Composable
fun RegistrationScreen() {
    var lastName by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var patronymic by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("0") }
    var weight by remember { mutableStateOf("0") }
    var birthDate by remember { mutableStateOf("") }
    var pasport by remember { mutableStateOf("") }
    var showIndicator by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    var availableDishes by remember { mutableStateOf<List<ServDish>>(emptyList()) }

    var selectedDish by remember { mutableStateOf<ServDish?>(null) }
    val registrationController: RegistrationController by localDI().instance()
    LaunchedEffect(true) {
        fetchDishesFromServer(registrationController) { dish ->
            availableDishes = dish
        }
        fetchPrisonersFromServer(registrationController){ prisonerModels ->
            println(prisonerModels)
        }
    }

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
                value = lastName,
                onValueChange = {
                    lastName = it
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
                value = firstName,
                onValueChange = {
                    firstName = it
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
                    patronymic = it
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
            OutlinedTextField(
                value = pasport,
                onValueChange = {
                    pasport = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                label = {
                    Text("Паспорт", color = Color.LightGray)
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(20.dp).fillMaxWidth())
            OutlinedTextField(
                value = birthDate,
                onValueChange = {
                    birthDate = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                label = {
                    Text("Дата рождения", color = Color.LightGray)
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
                        Text("Рост", color = Color.LightGray)
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
                        Text("Вес", color = Color.LightGray)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(20.dp))
            FavouriteDishSelectionDropdownMenu(
                availableDishes = availableDishes,
                onSelectDish = {
                    selectedDish = it
                },
                selectedDish = selectedDish
            )
            Spacer(Modifier.height(60.dp).fillMaxWidth())
            Button(
                onClick = {
                    scope.launch {
                        showIndicator = true
                        val prisonersModel =  RegisterPrisonerModel(
                            lastName = lastName,
                            firstName = firstName,
                            patronymic = patronymic,
                            passport = pasport,
                            weight = weight.toInt(),
                            birthDate = birthDate,
                            favoriteDish = selectedDish!!.id
                        )
                        println( registrationController.registerPrisoner(
                            prisonersModel
                        ))
                        delay(500)
                        showIndicator = false
                    }
                },
                enabled = selectedDish != null && firstName.isNotBlank() && lastName.isNotBlank() && pasport.isNotBlank() && birthDate.isNotBlank(),
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


suspend fun fetchDishesFromServer(
    registrationController: RegistrationController,
    onSuccess: (List<ServDish>) -> Unit
) {

    val dishes = registrationController.getDishes()

    dishes?.let { onSuccess(it.content) }
}

suspend fun fetchPrisonersFromServer(
    registrationController: RegistrationController,
    onSuccess: (List<GetPrisonersModel>) -> Unit
) {

    val prisoners = registrationController.getPrisoners()
    prisoners?.let { onSuccess(it.content) }
}