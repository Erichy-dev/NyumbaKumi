package com.erichydev.nyumbakumi.accountComposables

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseUser

@Composable
fun UserName(
    user: FirebaseUser?
) {
    // Define a set of vibrant gradient colors for the user's name
    val gradientColors = listOf(
        Color(0xFF4287f5), // Bright Blue
        Color(0xFF42f554), // Bright Green
        Color(0xFFF5b342), // Bright Orange-Yellow
        Color(0xFFF542a7), // Bright Pink
        Color(0xFF42f5f5), // Bright Cyan
        Color(0xFFF54242)  // Bright Red
    )

    // Infinite transition for color animation
    val infiniteTransition = rememberInfiniteTransition()

    // Create animations for each color in the gradient
    val color1 by infiniteTransition.animateColor(
        initialValue = gradientColors[0],
        targetValue = gradientColors[1],
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val color2 by infiniteTransition.animateColor(
        initialValue = gradientColors[1],
        targetValue = gradientColors[2],
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val color3 by infiniteTransition.animateColor(
        initialValue = gradientColors[2],
        targetValue = gradientColors[3],
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val color4 by infiniteTransition.animateColor(
        initialValue = gradientColors[3],
        targetValue = gradientColors[4],
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val color5 by infiniteTransition.animateColor(
        initialValue = gradientColors[4],
        targetValue = gradientColors[5],
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val color6 by infiniteTransition.animateColor(
        initialValue = gradientColors[5],
        targetValue = gradientColors[0], // Loop back to the first color for a seamless transition
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Create a dynamic gradient brush using animated colors
    val animatedGradientBrush = Brush.linearGradient(
        colors = listOf(color1, color2, color3, color4, color5, color6)
    )

    // Display the user's name with the animated gradient brush
    Column {
        Text(
            text = user?.displayName ?: "",
            fontSize = 50.sp,
            fontFamily = FontFamily.Cursive,
            style = TextStyle(
                brush = animatedGradientBrush // Apply the animated gradient brush
            ),
        )
    }
}

@Preview
@Composable
fun PreviewUserName() {
//    UserName()
}