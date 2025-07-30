package com.intel.ciao

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.intel.ciao.ui.theme.CiaoTheme

@Composable
fun HomeScreen(navController: androidx.navigation.NavController) {
    val color_background = Color(0XFFFFFAF3)
    val color_button = Color(0XFFFFECD2)
    val color_logo = Color(0XFF73AE19)
    val color_buttontext = Color(0XFF9E6C3A)

    var showButton by remember { mutableStateOf(false) }

    // 버튼 표시를 3000ms(텍스트 등장) + 800ms(애니메이션) 이후로 설정
    LaunchedEffect(Unit) {
        delay(3800)
        showButton = true
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            AnimatedTextItem("Ciao", 1000, 36, color_logo)
        }

        Column(modifier = Modifier.padding(vertical = 12.dp)) {
            AnimatedTextItem("빠르고 정확한 렌터카 점검을", 2000, null, Color.Black)
            AnimatedTextItem("쉽고 간편하게 경험해보세요!", 3000, null, Color.Black)
        }

        if (showButton) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { navController.navigate("login") },
                    colors = ButtonDefaults.buttonColors(containerColor = color_button),
                    modifier = Modifier
                        .width(360.dp)
                        .height(50.dp),
                    contentPadding = PaddingValues(10.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "시작하기",
                        color = color_buttontext,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}


@Composable
fun AnimatedTextItem(text: String, delayMillis: Int, fontsize: Int?, color: Color) {
    var visible by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing),
        label = "alpha"
    )

    val offsetY by animateDpAsState(
        targetValue = if (visible) 0.dp else (-20).dp,
        animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing),
        label = "offsetY"
    )

    // 시작 타이밍 조절
    LaunchedEffect(Unit) {
        delay(delayMillis.toLong())
        visible = true
    }

    if (fontsize != null) {
        Text(
            text = text,
            fontSize = fontsize.sp,
            modifier = Modifier
                .alpha(alpha)
                .offset(y = offsetY)
                .padding(vertical = 4.dp),
            color = color,
        )
    } else {
        Text(
            text = text,
            modifier = Modifier
                .alpha(alpha)
                .offset(y = offsetY)
                .padding(vertical = 4.dp),
            color = color,
        )
    }
}

