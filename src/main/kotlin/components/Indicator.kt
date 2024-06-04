package components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Indicator(
    size: Dp = 84.dp, // indicator size
    sweepAngle: Float = 90f, // angle (lenght) of indicator arc
    color: Color = Color.White, // color of indicator arc line
    strokeWidth: Dp = 6.dp,  //width of cicle and ar lines,
    cancelClickProvided: Boolean = false,
    onCancelClick: () -> Unit
) {
    ////// animation //////

    // docs recomend use transition animation for infinite loops
    // https://developer.android.com/jetpack/compose/animation
    val transition = rememberInfiniteTransition()

    var hovered by remember { mutableStateOf(false) }


    // define the changing value from 0 to 360.
    // This is the angle of the beginning of indicator arc
    // this value will change over time from 0 to 360 and repeat indefinitely.
    // it changes starting position of the indicator arc and the animation is obtained
    val currentArcStartAngle by transition.animateValue(
        0,
        360,
        Int.VectorConverter,
        infiniteRepeatable(
            animation = tween(
                durationMillis = 1100,
                easing = LinearEasing
            )
        )
    )


    ////// draw /////

    // define stroke with given width and arc ends type considering device DPI
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Square)
    }
    // draw on canvas
    if (hovered && cancelClickProvided) {
        IconButton(
            onClick = {
                onCancelClick()
            },
            modifier = Modifier
                .onPointerEvent(PointerEventType.Exit) { hovered = false }
        ) {
            Icon(Icons.Default.Cancel, "", tint = Color.DarkGray)
        }
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .onPointerEvent(PointerEventType.Enter) { hovered = true }
                .onPointerEvent(PointerEventType.Exit) { hovered = false }

        ) {
            Canvas(
                Modifier
                    .progressSemantics() // (optional) for Accessibility services
                    .size(size) // canvas size
                    .padding(strokeWidth / 2) //padding. otherwise, not the whole circle will fit in the canvas
            ) {
                // draw "dateTimePicker.styles.getBackground" (gray) circle with defined stroke.
                // without explicit center and radius it fit canvas bounds
                drawCircle(Color.LightGray, style = stroke)

                // draw arc with the same stroke
                drawArc(
                    color,
                    // arc start angle
                    // -90 shifts the start position towards the y-axis
                    startAngle = currentArcStartAngle.toFloat() - 90,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = stroke
                )
            }
        }
    }
}