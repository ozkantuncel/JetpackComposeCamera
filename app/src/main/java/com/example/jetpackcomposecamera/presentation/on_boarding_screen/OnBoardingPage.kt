package com.example.jetpackcomposecamera.presentation.on_boarding_screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetpackcomposecamera.presentation.common.button.JCCFinishButton
import com.example.jetpackcomposecamera.presentation.navigation.Screen
import com.example.jetpackcomposecamera.presentation.ui.theme.Color2App10
import com.example.jetpackcomposecamera.presentation.ui.theme.ColorApp60
import com.example.jetpackcomposecamera.util.hawk.Prefs.setShowOnBoardingState

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun OnBoardingScreen(
    navController: NavController,
) {
    val pages = listOf(
        OnBoardingItem.First,
        OnBoardingItem.Second,
        OnBoardingItem.Third
    )

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color2App10)
    ) {
        HorizontalPager(
            modifier = Modifier.weight(0.8f),
            pageCount = 3,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { position ->
            Pager(
                onBoardingPage = pages[position],
                pagerState,
                onClickButton = {
                    setShowOnBoardingState(true)
                    navController.popBackStack()
                    navController.navigate(Screen.LoginScreen.route)
                }
            )
        }

    }
}


@OptIn(ExperimentalFoundationApi::class)
@ExperimentalAnimationApi
@Composable
fun Pager(
    onBoardingPage: OnBoardingItem,
    pagerState: PagerState,
    onClickButton: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier
                .weight(0.5f)
                .size(300.dp),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = null,

            )
        Card(
            modifier = Modifier
                .align(Alignment.Start)
                .animateContentSize(
                    tween(
                        durationMillis = 500,
                        delayMillis = 100,
                        easing = LinearOutSlowInEasing
                    )
                ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Transparent
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    text = onBoardingPage.title,
                    style = TextStyle(Color.Black),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )

                JCCFinishButton(
                    modifier = Modifier,
                    pagerState = pagerState,
                    onClick = onClickButton
                )

                PageIndicator(
                    modifier = Modifier.padding(10.dp),
                    size = 3,
                    index = pagerState.currentPage
                )

            }
        }
    }
}

@Composable
fun PageIndicator(modifier: Modifier, size: Int, index: Int) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 18.dp else 16.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) ColorApp60
                else Color.Black
            )
    ) {

    }
}