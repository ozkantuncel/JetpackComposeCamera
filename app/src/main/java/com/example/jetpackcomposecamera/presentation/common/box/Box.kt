package com.example.jetpackcomposecamera.presentation.common.box

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposecamera.presentation.ui.theme.Color2App10

@Composable
fun JCCBox(
    modifier: Modifier = Modifier,
    floatColor:Int,
    padding: Dp = 8.dp,
    username:String,
    heightBox: Dp = 100.dp
) {


    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(start = padding, end = padding)
            .background(getCustomPinkColor(floatColor))
            .height(heightBox)
    ){
        Text(
            modifier = Modifier.padding(bottom = 10.dp).align(Alignment.Center),
            text = username,
            color = Color.Black,
            fontSize = 38.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}
private fun getCustomPinkColor(factor: Int): Color {
    val floatFactor = 1 - (factor / 10f)
    return Color(
        red = Color2App10.red * floatFactor,
        green = Color2App10.green,
        blue = Color2App10.blue * floatFactor
    )
}