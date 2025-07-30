package com.intel.ciao

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
    val color_red = Color(0XFFFF4444)
    val color_blue_button = Color(0XFF2196F3)

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .background(color_background),
        verticalArrangement = Arrangement.Center,

        ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                navController.navigate("photo")
            },
                colors = ButtonDefaults.buttonColors(containerColor = color_red),
                modifier = Modifier
                    .width(160.dp)
                    .height(240.dp),
                contentPadding = PaddingValues(10.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("대여하기",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
            }
            
            Button(onClick = {
                navController.navigate("photo")
            },
                colors = ButtonDefaults.buttonColors(containerColor = color_blue_button),
                modifier = Modifier
                    .width(160.dp)
                    .height(240.dp),
                contentPadding = PaddingValues(10.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("반납하기",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
            }
        }
    }
}