package screens.mainScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import components.TableCell
import components.TableHeader
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FloorAllocation() {
    Box(Modifier.fillMaxSize().padding(horizontal = 60.dp)) {
        var nameQuery by remember { mutableStateOf("") }
        val userList = mutableStateListOf<UserModel>()
        val scope = rememberCoroutineScope()
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
//                    Box(contentAlignment = Alignment.Center) {
//                        Button(onClick = {
//
//                        }, content = {
//                            Row {
//                                Icon(Icons.Default.FilterAlt, "")
//                                Text("Фильтр")
//                            }
//                        },
//                            colors = ButtonDefaults.buttonColors(
//                                backgroundColor = Color.White,
//                            ),
//                            modifier = Modifier.background(Color.White, shape = RoundedCornerShape(12.dp))
//                        )
//                    }
                }
                FloorTable(userList)
                Spacer(Modifier.padding(20.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(
                        onClick = {
                            scope.launch {
                                userList.sortByRank()
                            }
                            //todo allocate by rank action
                        },
                        content = {
                            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                                Text(
                                    text = "Распределить по баллам",
                                    textAlign = TextAlign.Center
                                )
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .height(60.dp)
                            .pointerHoverIcon(PointerIcon.Hand)
                    )
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = {
                            //todo confirm
                        },
                        content = {
                            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                                Text(
                                    text = "Подтвердить",
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
    }
}


@Composable
fun FloorTable(userList: SnapshotStateList<UserModel>) {
    LaunchedEffect(Unit) {
        userList.addAll(
            listOf(
                UserModel("123", "Пользователь 1", rank = 1.0, floor = 1),
                UserModel("123", "Пользователь 2", rank = 15.0, floor = 1),
                UserModel("123", "Пользователь 3", rank = 6.0, floor = 1),
                UserModel("123", "Пользователь 4", rank = 100.0, floor = 1),
                UserModel("123", "Пользователь 5", rank = 25.0, floor = 1),
                UserModel("123", "Пользователь 6", rank = 54.0, floor = 1),
                UserModel("123", "Пользователь 7", rank = 89.0, floor = 1),
                UserModel("123", "Пользователь 8", rank = 84.0, floor = 1),
                UserModel("123", "Пользователь 9", rank = 18.0, floor = 1),
                UserModel("123", "Пользователь 10", rank = 77.0, floor = 1),
                UserModel("123", "Пользователь 11", rank = 55.0, floor = 1),
                UserModel("123", "Пользователь 12", rank = 60.0, floor = 1),
                UserModel("123", "Пользователь 13", rank = 70.0, floor = 1),
                UserModel("123", "Пользователь 14", rank = 33.0, floor = 1),
            ).sortedBy { it.name }
        )
    }

    Column(){
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
            items(userList) { user ->
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
                            headerIcon = Icons.Default.Edit,
                            onClick ={

                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun AllocateByTypingDialogWindow(){

}

suspend fun SnapshotStateList<UserModel>.sortByRank() {
    val _userList = mutableListOf<UserModel>()
    _userList.addAll(this)
    this.clear()
    delay(150)
    _userList.sortByDescending { it.rank }
    this.addAll(_userList)
}

data class UserModel(
    val id: String,
    val name: String,
    val rank: Double,
    val floor: Int,
)