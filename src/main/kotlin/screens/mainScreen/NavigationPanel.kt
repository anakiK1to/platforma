package screens.mainScreen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class MainScreenNavigation(val title: String) {
    USER_MANAGEMENT("Пльзователи"),
    REGISTRATION("Регистрация"),
    MENU_CONFIGURATION("Меню"),
    POINTS_MANAGEMENT("Баллы"),
    NEW_ISSUE("Происшествие"),
    FLOOR_ALLOCATION("Этажи")
}

@Composable
fun NavigationPanel(onClick: (MainScreenNavigation) -> Unit) {
    val screens = MainScreenNavigation.entries.toTypedArray()
    var selectedItem by remember { mutableStateOf(0) }
    val icons = listOf(
        Icons.Filled.Settings,
        Icons.Filled.Add,
        Icons.Filled.AdminPanelSettings,
        Icons.Filled.Markunread,
        Icons.Filled.Chat,
        Icons.Filled.ChangeCircle
    )
    Row() {
        NavigationRail(Modifier.width(120.dp)) {
            screens.forEachIndexed { index, screen ->
                when (screen) {
                    MainScreenNavigation.USER_MANAGEMENT -> {

                        NavigationRailItem(
                            icon = { Icon(icons[index], contentDescription = "") },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                onClick(screens[index])
                            },
                            label = {
                                Text(screen.title)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    MainScreenNavigation.REGISTRATION -> {
                        NavigationRailItem(
                            icon = { Icon(icons[index], contentDescription = "") },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                onClick(screens[index])
                            },
                            label = {
                                Text(screen.title)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    MainScreenNavigation.MENU_CONFIGURATION -> {
                        NavigationRailItem(
                            icon = { Icon(icons[index], contentDescription = "") },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                onClick(screens[index])
                            },
                            label = {
                                Text(screen.title)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    MainScreenNavigation.POINTS_MANAGEMENT -> {
                        NavigationRailItem(
                            icon = { Icon(icons[index], contentDescription = "") },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                onClick(screens[index])
                            },
                            label = {
                                Text(screen.title)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    MainScreenNavigation.NEW_ISSUE -> {
                        NavigationRailItem(
                            icon = { Icon(icons[index], contentDescription = "") },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                onClick(screens[index])
                            },
                            label = {
                                Text(screen.title)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    MainScreenNavigation.FLOOR_ALLOCATION -> {
                        NavigationRailItem(
                            icon = { Icon(icons[index], contentDescription = "") },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                onClick(screens[index])
                            },
                            label = {
                                Text(screen.title)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}