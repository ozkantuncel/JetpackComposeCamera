package com.example.jetpackcomposecamera.presentation.common.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposecamera.presentation.ui.theme.ColorApp30

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalAnimationApi
@Composable
fun JCCFinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 55.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == 2
        ) {
            Button(
                modifier= modifier
                    .height(50.dp)
                    .width(200.dp),
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    backgroundColor = ColorApp30
                ),
                shape = RoundedCornerShape(50)
            ) {
                Text(text = "Geç", color = Color.White)
            }
        }
    }
}