package com.erichydev.nyumbakumi.plotComposables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun Loader() {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val firstCircleSize by infiniteTransition.animateFloat(
        initialValue = 7f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val secondCircleSize by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 7f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val thirdCircleSize by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 7f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val spacing = with(LocalDensity.current) { 19.dp.toPx() }
    val baseRadius = with(LocalDensity.current) { 4.dp.toPx() }
    val offset = with(LocalDensity.current) { -38.dp.toPx() }

    Scaffold {
        Box(
            modifier = Modifier.padding(it).fillMaxWidth()
        ){
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .zIndex(3f)
                    .height(40.dp)
                    .width(50.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = Color.White,
                        radius = baseRadius + firstCircleSize,
                        center = center.copy(x = center.x + offset + spacing * 1)
                    )
                    drawCircle(
                        color = Color.White,
                        radius = baseRadius + secondCircleSize,
                        center = center.copy(x = center.x + offset + spacing * 2)
                    )
                    drawCircle(
                        color = Color.White,
                        radius = baseRadius + thirdCircleSize,
                        center = center.copy(x = center.x + offset + spacing * 3)
                    )
                }
            }
        }
    }
}