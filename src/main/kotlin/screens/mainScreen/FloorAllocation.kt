package screens.mainScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.TableCell
import components.TableHeader

@Composable
fun FloorAllocation() {
    Box(Modifier.fillMaxSize().padding(horizontal = 60.dp), contentAlignment = Alignment.Center) {
        var nameQuery by remember { mutableStateOf("") }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Column(Modifier.padding(10.dp), horizontalAlignment = Alignment.Start) {
                Text(
                    "Управление этажами",
                    fontWeight = FontWeight.Bold,
                )
                Row {
                    OutlinedTextField(
                        value = nameQuery,
                        onValueChange = {
                            nameQuery = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp
                        ),
                        label = {
                            Text("Поиск пользователей", color = Color.LightGray)
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Search, "")
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                    Button(onClick = {

                    }, content = {
                        Row {
                            Icon(Icons.Default.Filter, "")
                            Text("Фильтр")
                        }
                    })
                }
                FloorTable()
            }
        }
    }
}


@Composable
fun FloorTable() {
    val userList = mutableStateListOf<UserModel>()
    LaunchedEffect(Unit) {
        userList.addAll(
            listOf(
                UserModel("123", "Пользователь 1", rank = 1, floor = 1),
                UserModel("123", "Пользователь 1", rank = 1, floor = 1),
                UserModel("123", "Пользователь 1", rank = 1, floor = 1),
                UserModel("123", "Пользователь 1", rank = 1, floor = 1),
                UserModel("123", "Пользователь 1", rank = 1, floor = 1),
                UserModel("123", "Пользователь 1", rank = 1, floor = 1),
                UserModel("123", "Пользователь 1", rank = 1, floor = 1),
                UserModel("123", "Пользователь 1", rank = 1, floor = 1),
                UserModel("123", "Пользователь 1", rank = 1, floor = 1),
                UserModel("123", "Пользователь 1", rank = 1, floor = 1),
                UserModel("123", "Пользователь 1", rank = 1, floor = 1),
                UserModel("123", "Пользователь 1", rank = 1, floor = 1),
                UserModel("123", "Пользователь 1", rank = 1, floor = 1),
                UserModel("123", "Пользователь 1", rank = 1, floor = 1),
            )
        )
    }
    Column {
        Row {
            TableHeader(
                headerName = "Этаж"
            )
            TableHeader(
                headerName = "Имя Пользователя"
            )
            TableHeader(
                headerName = "Баллы"
            )
            TableHeader(
                headerName = "Изменить"
            )
        }
        LazyColumn {
            items(userList){ user ->
                Column {
                    Divider(Modifier.fillMaxWidth().height(1.dp))

                    Row {
                        TableCell(
                            user.floor.toString(),
                            color = Color.White,
                            weight = 1f
                        )
                        TableCell(
                            user.name,
                            color = Color.White,
                            weight = 1f
                        )
                        TableCell(
                            user.rank.toString(),
                            color = Color.White,
                            weight = 1f
                        )
                        TableCell(
                            user.floor.toString(),
                            color = Color.White,
                            weight = 1f
                        )
                    }
                }
            }
        }
    }
}

data class UserModel(
    val id: String,
    val name: String,
    val rank: Int,
    val floor: Int,
)