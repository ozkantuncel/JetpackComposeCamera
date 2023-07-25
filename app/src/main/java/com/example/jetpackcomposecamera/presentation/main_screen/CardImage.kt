package com.example.jetpackcomposecamera.presentation.main_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackcomposecamera.R
import com.example.jetpackcomposecamera.data.model.ImageModel
import com.example.jetpackcomposecamera.presentation.ui.theme.Color2App10

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardImage(
    image: ImageModel,
    onButtonClick: (ImageModel) -> Unit
) {
    var expand by remember {
        mutableStateOf(false)
    }
    var stroke by remember { mutableStateOf(1) }

    Card(modifier = Modifier
        .animateContentSize(
            animationSpec = tween(
                durationMillis = 400,
                easing = LinearOutSlowInEasing
            )
        )
        .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        onClick = {
            expand = !expand
            stroke = if (expand) 2 else 1
        }
    ) {
        // Row bileşeni, bileşenleri yatay yönde bir düzende yerleştirmek için kullanılır
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = image.image_dir),
                contentDescription = null,
                modifier = Modifier
                    .size(if (expand) 300.dp else 150.dp)
                    .animateContentSize()
            )
            if (!expand) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color2App10),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {


                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = R.drawable.rick_picture),
                        contentDescription = null
                    )
                    Text(
                        text = image.user_name,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(
                        text = image.image_name.substring(0, 16),
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    IconButton(onClick = { onButtonClick(image) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.del_ico),
                            contentDescription = null
                        )
                    }

                }
            }
        }
    }
}