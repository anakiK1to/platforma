package screens.mainScreen.menuConfiguration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import models.AddDishModel
import models.Ingredient
import models.Product
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Composable
fun MenuConfiguration() {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var receipt by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val menuConfigurationController: MenuConfigurationController by localDI().instance()

    var availableIngredients by remember { mutableStateOf<List<Product>>(emptyList()) }
    var selectedIngredients by remember { mutableStateOf<List<Ingredient>>(emptyList()) }



    LaunchedEffect(true) {
        fetchIngredientsFromServer(menuConfigurationController) { product ->
            availableIngredients = product
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(Modifier.width(450.dp)) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                label = {
                    Text("Название", color = Color.LightGray)
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(20.dp).fillMaxWidth())
            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                label = {
                    Text("Описание", color = Color.LightGray)
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(20.dp).fillMaxWidth())
            OutlinedTextField(
                value = receipt,
                onValueChange = {
                    receipt = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                label = {
                    Text("Рецепт", color = Color.LightGray)
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(20.dp).fillMaxWidth())
            LazyColumn(Modifier.height(380.dp)) {
                items(availableIngredients) { product ->
                    Row {
                        var counter by remember { mutableStateOf("0") }
                        var added by remember { mutableStateOf(false) }
                        val icon by derivedStateOf {
                            if (added)
                                Icons.Filled.Remove
                            else Icons.Filled.Add
                        }
                        Text(
                            product.name,
                            modifier = Modifier.weight(1f),
                        )
                        OutlinedTextField(
                            value = counter,
                            onValueChange = {
                                counter = it
                            },
                            textStyle = TextStyle(
                                fontSize = 16.sp
                            ),
                            label = {
                                Text("", color = Color.LightGray)
                            },
                            singleLine = true,
                            enabled = !added,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.width(50.dp)
                        )
                        IconButton(onClick = {
                            added = !added
                            if (added) {
                                selectedIngredients += Ingredient(
                                    amount = counter.toInt(),
                                    productId = product.id
                                )
                            } else {
                                selectedIngredients -= Ingredient(
                                    amount = counter.toInt(),
                                    productId = product.id
                                )
                            }
                        }, content = {
                            Icon(icon, "")
                        },
                            enabled = counter != "0" && counter.isNotBlank())
                    }
                }
            }

            Spacer(Modifier.height(60.dp).fillMaxWidth())

            Button(
                onClick = {
                    scope.launch {
                        println(
                            menuConfigurationController.addDish(
                                addDishModel = AddDishModel(
                                    name = name,
                                    description = description,
                                    receipt = receipt,
                                    ingredients = selectedIngredients
                                )
                            )
                        )
                    }
                },
                content = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//                if (showIndicator)
//                    Indicator(
//                        size = 36.dp,
//                        onCancelClick = {}
//                    )
//                else
                        Text(
                            text = "Добавить блюдо",
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

suspend fun fetchIngredientsFromServer(
    menuConfigurationController: MenuConfigurationController,
    onSuccess: (List<Product>) -> Unit
) {

    val ingredients = menuConfigurationController.getProducts()
    ingredients?.let { onSuccess(it.content) }
}

