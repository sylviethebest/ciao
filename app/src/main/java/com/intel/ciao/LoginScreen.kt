package com.intel.ciao

import android.R.style.Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Visibility
//import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intel.ciao.ui.theme.CiaoTheme
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.PasswordVisualTransformation


@Composable
fun LoginScreen(navController: androidx.navigation.NavController) {
    val color_background = Color(0XFFFFFAF3)
    val color_button = Color(0XFFFFECD2)
    val color_logo = Color(0XFF73AE19)
    val color_buttontext = Color(0XFF9E6C3A)
    val color_blue = Color(0XFF2D87E2)

    var getid by remember { mutableStateOf("") }
    var setid by remember { mutableStateOf("") }
    var getpassward by remember { mutableStateOf("") }
    var setpassward by remember { mutableStateOf("") }

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
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("아이디",
                    color = color_buttontext,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp).
                    width(70.dp)
                )
                TextField(
                    value = getid,
                    onValueChange = { getid = it },
                    singleLine = true,
                    modifier = Modifier.width(250.dp)
                        .border(width = 1.dp, color = color_buttontext),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    )
                )
            }

            Spacer(Modifier.height(20.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text("비밀번호",
                    color = color_buttontext,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp).
                    width(70.dp),
                )
                PasswordTextField(getpassward, { getpassward = it })
            }

            Spacer(Modifier.height(20.dp))

            Button(onClick = {
                setid = getid
                navController.navigate("second")
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

            Text(setid,
                color = color_buttontext,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(16.dp).
                width(70.dp),
            )
        }
    }
}

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
) {
    val isPasswordVisible = remember { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = onPasswordChange,
        visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        modifier = Modifier.width(250.dp)
            .border(width = 1.dp, color = Color(0XFF9E6C3A)),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White
        )
    )
}