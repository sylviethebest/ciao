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

@Composable
fun SubmitScreen(navController: androidx.navigation.NavController) {
    val color_background = Color(0XFFFFFAF3)
    val color_button = Color(0XFFFFECD2)
    val color_logo = Color(0XFF73AE19)
    val color_buttontext = Color(0XFF9E6C3A)
    val color_blue = Color(0XFF2D87E2)

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,

        ) {
        Column (
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "사전 점검이",
                modifier = Modifier
                    .padding(vertical = 4.dp),
                color = color_buttontext,
                fontSize = 36.sp
            )
            Text(
                text = "완료되었습니다.",
                modifier = Modifier
                    .padding(vertical = 4.dp),
                color = color_buttontext,
                fontSize = 36.sp
            )
        }
        Column (
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Text(
                text = "어쩌구 저쩌구",
                modifier = Modifier
                    .padding(vertical = 4.dp),
            )
            Text(
                text = "어쩌구 입니다.",
                modifier = Modifier
                    .padding(vertical = 4.dp),
            )
        }
        Column (
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                navController.navigate("login")
            },
                colors = ButtonDefaults.buttonColors(containerColor = color_button),
                modifier = Modifier.width(360.dp).height(50.dp),
                contentPadding = PaddingValues(10.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("확인",
                    color = color_buttontext,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
        }
    }
}
