package com.erichydev.nyumbakumi.signInComposables

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay

@Composable
fun AnimatedButton(
    func: () -> Unit,
    textContent: String,
    gradientColors: List<Color> = listOf(
        Color(0xFFFFC107), // Amber color
        Color(0xFFFF9800), // Deep Orange color
        Color(0xFF4CAF50), // Green color
        Color(0xFF2196F3), // Blue color
        Color(0xFF3F51B5), // Indigo color
        Color(0xFF9C27B0), // Purple color
        Color(0xFFF44336), // Red color
        Color(0xFF795548), // Brown color
        Color(0xFF607D8B)  // Blue Grey color
    )
) {
    var currentColors by remember { mutableStateOf(gradientColors) }
    var nextColors by remember { mutableStateOf(gradientColors.shuffled()) }

    val transition = rememberInfiniteTransition(label = "gradientTransition")

    val animatedColors = currentColors.mapIndexed { index, color ->
        transition.animateColor(
            initialValue = color,
            targetValue = nextColors[index],
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 500, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ), label = "animeBackgroundGrad"
        )
    }

    LaunchedEffect(Unit) {
        while(true) {
            currentColors = nextColors
            nextColors = gradientColors.shuffled()
            delay(500)
        }
    }

    val boxSize = remember {
        mutableStateOf(IntSize(0, 0))
    }
    val density = LocalDensity.current
    // Apply the gradient background to a Box
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                func()
            }
            .onGloballyPositioned { layoutCoordinates ->
                boxSize.value = with(density) { layoutCoordinates.size }
            }
            .background(
                brush = Brush.linearGradient(
                    colors = animatedColors.map { it.value },
                    start = Offset(0f, 0f),
                    end = Offset(
                        boxSize.value.width.toFloat(),
                        0f
                    ) // Change this to control the direction of the gradient
                )
            )
            .padding(2.dp) // Add padding if needed
    ) {
        // Add any content you want inside this Box
        Column(
            modifier = Modifier
                .background(Color(0xFF073B4C), shape = RoundedCornerShape(10.dp))
                .padding(vertical = 20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = textContent,
                fontSize = 13.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
