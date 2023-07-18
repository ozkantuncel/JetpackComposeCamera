package com.example.jetpackcomposecamera.presentation.common.box

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposecamera.presentation.ui.theme.Pink80

@Composable
fun JCCBox(
    modifier: Modifier = Modifier,
    padding: Dp = 8.dp,
    colorBox: Color = Pink80,
    heightBox: Dp = 100.dp
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(start = padding, end = padding)
            .background(
                colorBox
            )
            .height(heightBox)
    )
}