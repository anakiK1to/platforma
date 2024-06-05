package screens.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuConfiguration() {
    Column {
        Row(Modifier.fillMaxWidth()) {
            Text(
                "Завтрак для Пользователь 1",
                modifier = Modifier.align(Alignment.CenterVertically).padding(horizontal = 32.dp),
            )
            Box(Modifier.fillMaxWidth().padding(horizontal = 16.dp), contentAlignment = Alignment.CenterEnd) {
                Row(Modifier) {
                    TextButton(onClick = {

                    },
                        modifier = Modifier.align(Alignment.CenterVertically).padding(horizontal = 16.dp)
                            .pointerHoverIcon(PointerIcon.Hand),
                        content = {
                            Text(
                                "Предыдущий пользователь",
                                fontSize = 16.sp
                            )
                        })
                    TextButton(onClick = {

                    },
                        modifier = Modifier.align(Alignment.CenterVertically).padding(horizontal = 16.dp)
                            .pointerHoverIcon(PointerIcon.Hand),
                        content = {
                            Text(
                                "Следующий пользователь",
                                fontSize = 16.sp
                            )
                        })
                    Button(
                        onClick = {
                            //todo confirm
                        },
                        content = {
                            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                                Text(
                                    text = "Подвердить заказ",
                                    textAlign = TextAlign.Center
                                )
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .padding(5.dp)
                            .pointerHoverIcon(PointerIcon.Hand)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            val imageModifier = Modifier
                .height(500.dp)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(12.dp))

            Image(painter = painterResource("porridge.png"),
                contentDescription = "porridge",
                imageModifier,
                contentScale = ContentScale.FillHeight
            )
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                Column {
                    Text(
                        "Каша",
                        fontSize = 32.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Комментарий",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Протеин: г в 100г - ",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Жиры: г в 100 г",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Углеводы: г в 100 г",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}