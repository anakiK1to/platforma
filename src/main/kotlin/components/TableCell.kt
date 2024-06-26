package components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.TableHeader(
    headerName: String,
    weight: Float = 0.5f,
) {
    Box(
        Modifier
            .weight(weight)
            .padding(
                PaddingValues(
                    vertical = 1.dp,
                    horizontal = 1.dp
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = headerName,
            color = Color.Black,
            modifier = Modifier
                .padding(8.dp),
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun RowScope.TableHeader(
    headerIcon: ImageVector,
    weight: Float = 0.5f
){
    Box(
        Modifier
            .weight(weight)
            .padding(
                PaddingValues(
                    vertical = 1.dp,
                    horizontal = 1.dp
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(headerIcon, "",
            modifier = Modifier
                .padding(8.dp)
        )
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.TableCell(
    headerIcon: ImageVector,
    weight: Float = 1f,
    onClick: () -> Unit
){
    Box(
        Modifier
            .weight(weight)
            .padding(
                PaddingValues(
                    vertical = 1.dp,
                    horizontal = 1.dp
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(headerIcon, "",
            modifier = Modifier
                .padding(8.dp)
                .onClick {
                    onClick()
                }
                .pointerHoverIcon(PointerIcon.Hand)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.TableCell(
    columnName: String,
    weight: Float,
    selected: Boolean = false,
    color: Color,
    onClick: () -> Unit = {},
    maxLines: Int = 1,
    hoverable: Boolean = false,
    clickable: Boolean = false
) {
    val hoverIcon by derivedStateOf { if (hoverable) PointerIcon.Hand else PointerIcon.Default }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .weight(weight)
            .padding(
                PaddingValues(
                    vertical = 1.dp,
                    horizontal = 1.dp
                )
            )
            .background(
                if (selected)
                    Color.DarkGray
                else if (clickable)
                    color.copy(alpha = 0.4f)
                else
                    color
            )
            .onClick {
                onClick()
            }
            .pointerHoverIcon(hoverIcon)
    ) {
        Text(
            columnName,
            Modifier
                .padding(10.dp),
            color = if (selected) Color.White else Color.Black,
            textAlign = TextAlign.Center,
            maxLines = maxLines,

            )
    }
}