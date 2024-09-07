package com.erichydev.nyumbakumi.homeComposables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun SparklesAround(
    onAnimationEnd: () -> Unit
) {
    val sparkles = remember { generateSparkles(30) } // Generate more sparkles

    sparkles.forEach { sparkle ->
        RadiatingSparkle(
            color = sparkle.color,
            initialOffset = sparkle.offset,
            onAnimationEnd
        )
    }
}

@Composable
fun RadiatingSparkle(
    color: Color,
    initialOffset: Offset,
    onAnimationEnd: () -> Unit
) {
    // Define animatables for scale, alpha, and offset
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0.3f) }
    val offset = remember { Animatable(0f) }

    // State to manage visibility
    var isVisible by remember { mutableStateOf(true) }

    // Run the animations once
    LaunchedEffect(Unit) {
        // Animate scale and offset concurrently
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(250, easing = LinearEasing)
            )
        }

        launch {
            offset.animateTo(
                targetValue = 13f,
                animationSpec = tween(250, easing = LinearEasing)
            )
        }

        // Animate alpha: fade in to 1f, hold, then fade out
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(230, easing = LinearEasing)
        )
        delay(10) // Hold at max alpha for 200ms
        alpha.animateTo(
            targetValue = 0f,
            animationSpec = tween(10, easing = LinearEasing)
        )

        // Set visibility to false after animation ends
        isVisible = false
        onAnimationEnd()
    }

    // Calculate new position based on original angle
    val angle = kotlin.math.atan2(initialOffset.y, initialOffset.x) // Calculate angle from offset
    val xOffset = cos(angle) * offset.value
    val yOffset = sin(angle) * offset.value

    if (isVisible) {
        Canvas(
            modifier = Modifier
                .size(3.dp)
                .offset((initialOffset.x + xOffset).dp, (initialOffset.y + yOffset).dp) // Apply animated offset
                .alpha(alpha.value) // Apply animated alpha
        ) {
            drawCircle(
                color = color,
                radius = 3f * scale.value // Apply animated scale
            )
        }
    }
}

data class SparkleData(val color: Color, val offset: Offset)

// Function to generate random sparkles within the entire circle area
fun generateSparkles(count: Int): List<SparkleData> {
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Magenta, Color.Cyan)
    val radius = 40f // Set a fixed radius for generating sparkles

    return List(count) {
        val angle = Random.nextFloat() * (2 * Math.PI).toFloat()
        // Use full radius to spread sparkles over the entire circle
        val distance = Random.nextFloat().let { it * it } * radius // Random distance using squared value for better distribution
        val xOffset = cos(angle) * distance
        val yOffset = sin(angle) * distance
        SparkleData(
            color = colors.random(),
            offset = Offset(xOffset, yOffset)
        )
    }
}