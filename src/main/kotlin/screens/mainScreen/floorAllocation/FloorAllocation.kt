package screens.mainScreen.floorAllocation

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.Indicator
import components.TableCell
import components.TableHeader
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import models.PlatformStructure
import models.ScoreAdditionalModel
import models.SubtractRequest
import models.Violations
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Composable
fun FloorAllocation() {
    Box(Modifier.fillMaxSize().padding(horizontal = 60.dp)) {
        var nameQuery by remember { mutableStateOf("") }
        val userList = mutableStateListOf<UserModel>()
        val scope = rememberCoroutineScope()
        val floorAllocationController: FloorAllocationController by localDI().instance()
        var platformStructure by remember { mutableStateOf<List<PlatformStructure>>(emptyList()) }
        var violations by remember { mutableStateOf<List<Violations>>(emptyList()) }
        var showIndicator by remember { mutableStateOf(false) }
        LaunchedEffect(showIndicator) {
            fetchDistribution(floorAllocationController) { println(it) }
            delay(200)
            fetchPlatformStructure(floorAllocationController) {
                platformStructure = it
            }
            fetchViolations(floorAllocationController) {
                violations = it
            }
        }

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
                }
                if (showIndicator) {
                    Indicator(
                        onCancelClick = {}
                    )
                } else {
                    FloorTable(platformStructure,
                        violations = violations,
                        onSelectViolate = { violations, prisonerId ->
                            scope.launch {
                                showIndicator = true
                                requestSubtraction(floorAllocationController, violations, prisonerId)
                                showIndicator = false
                            }
                        }, onAdd = { score, personId ->
                            scope.launch {
                                showIndicator = true
                                requestAddition(floorAllocationController, personId, score.toFloat())
                                showIndicator = false
                            }
                        })
                    Spacer(Modifier.padding(20.dp))
                }
//                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
//                    Button(
//                        onClick = {
//                            scope.launch {
//                                userList.sortByRank()
//                            }
//                            //todo allocate by rank action
//                        },
//                        content = {
//                            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
//                                Text(
//                                    text = "Распределить по баллам",
//                                    textAlign = TextAlign.Center
//                                )
//                            }
//                        },
//                        shape = RoundedCornerShape(12.dp),
//                        modifier = Modifier
//                            .height(60.dp)
//                            .pointerHoverIcon(PointerIcon.Hand)
//                    )
//                    Spacer(Modifier.width(10.dp))
//                    Button(
//                        onClick = {
//                            //todo confirm
//                        },
//                        content = {
//                            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
//                                Text(
//                                    text = "Подтвердить",
//                                    textAlign = TextAlign.Center
//                                )
//                            }
//                        },
//                        shape = RoundedCornerShape(12.dp),
//                        modifier = Modifier
//                            .height(60.dp)
//                            .pointerHoverIcon(PointerIcon.Hand)
//                    )
//                }
            }
        }
    }
}


@Composable
fun FloorTable(
    platformStructure: List<PlatformStructure>,
    violations: List<Violations>,
    onSelectViolate: (Violations, String) -> Unit,
    onAdd: (Int, String) -> Unit,
) {

    Column() {
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
                headerName = "Понизить"
            )
            TableHeader(
                headerName = "Повысить"
            )
        }
        LazyColumn {
            items(platformStructure) { platformStructure ->
                Column {
                    Divider(Modifier.fillMaxWidth().height(1.dp))

                    Row(
                    ) {
                        Box(
                            Modifier
                                .weight(1f)
                                .padding(
                                    PaddingValues(
                                        vertical = 1.dp,
                                        horizontal = 1.dp
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(Modifier.height(100.dp)) {
                                TableCell(
                                    platformStructure.floor.toString(),
                                    color = Color.White,
                                    weight = 1f
                                )
                            }
                        }
                        Box(
                            Modifier
                                .weight(1f)
                                .padding(
                                    PaddingValues(
                                        vertical = 1.dp,
                                        horizontal = 1.dp
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column {
                                Row {
                                    if (platformStructure.firstPrisoner != null)
                                        TableCell(
                                            "${platformStructure.firstPrisoner.firstName} ${platformStructure.firstPrisoner.lastName}",
                                            color = Color.White,
                                            weight = 1f
                                        )
                                }
                                Row {
                                    if (platformStructure.secondPrisoner != null)
                                        TableCell(
                                            "${platformStructure.secondPrisoner.firstName} ${platformStructure.secondPrisoner.lastName}",
                                            color = Color.White,
                                            weight = 1f
                                        )
                                }
                            }
                        }
                        Box(
                            Modifier
                                .weight(1f)
                                .padding(
                                    PaddingValues(
                                        vertical = 1.dp,
                                        horizontal = 1.dp
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column {
                                if (platformStructure.firstPrisoner != null)
                                    Row {
                                        TableCell(
                                            platformStructure.firstPrisoner.rating.toString(),
                                            color = Color.White,
                                            weight = 1f
                                        )
                                    }
                                if (platformStructure.secondPrisoner != null)
                                    Row {
                                        TableCell(
                                            platformStructure.secondPrisoner.rating.toString(),
                                            color = Color.White,
                                            weight = 1f
                                        )
                                    }
                            }
                        }
                        Box(
                            Modifier
                                .weight(1f)
                                .padding(
                                    PaddingValues(
                                        vertical = 1.dp,
                                        horizontal = 1.dp
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column {
                                if (platformStructure.firstPrisoner != null)
                                    Row {
                                        ViolationsDropDownMenu(
                                            availableViolations = violations,
                                            onSelectViolate = { violation ->
                                                onSelectViolate(violation, platformStructure.firstPrisoner.id)
                                            }
                                        )
                                    }
                                if (platformStructure.secondPrisoner != null)
                                    Row {
                                        ViolationsDropDownMenu(
                                            availableViolations = violations,
                                            onSelectViolate = { violation ->
                                                onSelectViolate(violation, platformStructure.secondPrisoner.id)
                                            }
                                        )
                                    }
                            }
                        }
                        Box(
                            Modifier
                                .weight(1f)
                                .padding(
                                    PaddingValues(
                                        vertical = 1.dp,
                                        horizontal = 1.dp
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column {
                                Row() {
                                    var value by remember { mutableStateOf("0") }
                                    OutlinedTextField(
                                        value = value,
                                        onValueChange = {
                                            value = it
                                        },
                                        textStyle = TextStyle(
                                            fontSize = 16.sp
                                        ),
                                        singleLine = true,
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.width(60.dp).padding(vertical = 5.dp)
                                    )
                                    if (platformStructure.firstPrisoner != null)
                                        IconButton(
                                            onClick = {
                                                onAdd(value.toInt(), platformStructure.firstPrisoner.id)
                                            },
                                            content = {
                                                Icon(Icons.Default.Add, "")
                                            }
                                        )
                                }
                                Row() {
                                    var value by remember { mutableStateOf("0") }
                                    OutlinedTextField(
                                        value = value,
                                        onValueChange = {
                                            value = it
                                        },
                                        textStyle = TextStyle(
                                            fontSize = 14.sp
                                        ),

                                        singleLine = true,
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.width(60.dp).padding(vertical = 5.dp)
                                    )
                                    if (platformStructure.secondPrisoner != null)
                                        IconButton(
                                            onClick = {
                                                onAdd(value.toInt(), platformStructure.secondPrisoner.id)
                                            },
                                            content = {
                                                Icon(Icons.Default.Add, "")
                                            }
                                        )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.ViolationsDropDownMenu(
    availableViolations: List<Violations>,
    onSelectViolate: (Violations) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    TableCell(
        headerIcon = Icons.Default.Edit,
        onClick = {
            expanded = true
        },
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        availableViolations.forEach { violate ->
            DropdownMenuItem(onClick = {
                onSelectViolate(violate)
                expanded = false
            }) {
                Row {
                    Text(violate.name)
                    Text(violate.score.toString())
                }
            }
        }
    }
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


suspend fun fetchDistribution(
    floorAllocationController: FloorAllocationController, onSuccess: (String) -> Unit
) {
    onSuccess(floorAllocationController.distribute())
}

suspend fun fetchViolations(
    floorAllocationController: FloorAllocationController, onSuccess: (List<Violations>) -> Unit
) {
    floorAllocationController.getViolations()?.let { onSuccess(it) }
}

suspend fun fetchPlatformStructure(
    floorAllocationController: FloorAllocationController,
    onSuccess: (List<PlatformStructure>) -> Unit
) {
    floorAllocationController.getPlatformStructure()?.let { onSuccess(it.content) }
}

suspend fun requestSubtraction(
    floorAllocationController: FloorAllocationController,
    violations: Violations,
    prisonerId: String
): Boolean {
    return floorAllocationController.subtract(SubtractRequest(personId = prisonerId, violationCode = violations.code))
}

suspend fun requestAddition(
    floorAllocationController: FloorAllocationController,
    personId: String,
    score: Float
): Boolean {
    return floorAllocationController.add(ScoreAdditionalModel(personId = personId, score = score))
}