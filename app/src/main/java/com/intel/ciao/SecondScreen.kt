package com.intel.ciao

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
fun SecondScreen(navController: androidx.navigation.NavController) {
    val color_background = Color(0XFFFFFAF3)
    val color_button = Color(0XFFFFECD2)
    val color_logo = Color(0XFF73AE19)
    val color_buttontext = Color(0XFF9E6C3A)
    val color_blue = Color(0XFF2D87E2)

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .background(color_background),
        verticalArrangement = Arrangement.Center,

        ) {
        Column (
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                navController.navigate("photo")
            },
                colors = ButtonDefaults.buttonColors(containerColor = color_button),
                modifier = Modifier.width(360.dp).height(50.dp),
                contentPadding = PaddingValues(10.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("대여하기",
                    color = color_buttontext,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
            Spacer(Modifier.height(100.dp))
            Button(onClick = {
                navController.navigate("photo")
            },
                colors = ButtonDefaults.buttonColors(containerColor = color_button),
                modifier = Modifier.width(360.dp).height(50.dp),
                contentPadding = PaddingValues(10.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("반납하기",
                    color = color_buttontext,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
        }
    }
}