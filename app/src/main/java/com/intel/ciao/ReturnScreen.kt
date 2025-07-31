package com.intel.ciao


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReturnScreen(navController: androidx.navigation.NavController) {
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
                text = "반납 점검이",
                modifier = Modifier
                    .padding(vertical = 4.dp),
                color = color_buttontext,
                fontSize = 36.sp,
            )
            Text(
                text = "완료되었습니다.",
                modifier = Modifier
                    .padding(vertical = 4.dp),
                color = color_buttontext,
                fontSize = 36.sp,
            )
        }
        Column (
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Text(
                text = "차오를 이용해주셔서 감사합니다.",
                modifier = Modifier
                    .padding(vertical = 4.dp),
            )
            Text(
                text = "안전하고 즐거운 하루 되세요!",
                modifier = Modifier
                    .padding(vertical = 4.dp),
            )
        }
        Column (
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                navController.navigate("home")
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