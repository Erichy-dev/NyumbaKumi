package com.erichydev.nyumbakumi.homeComposables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun AnimatedImage(
    isScaledDown: Boolean,
    setIsScaledDown: (Boolean) -> Unit,
) {
    val scale by animateFloatAsState(
        targetValue = if (isScaledDown) 0f else 1f,
        animationSpec = tween(durationMillis = 500)
    )

    val rotation by animateFloatAsState(
        targetValue = if (isScaledDown) 8640f else 0f, // Three full rotations (1080 degrees) when scaled down
        animationSpec = tween(durationMillis = 500)
    )

    val coroutineScope = rememberCoroutineScope()

    val imageHeight = remember { mutableStateOf(0.dp) }
    val imageWidth = remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box(modifier = Modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724751469/nyumba_kumi/icons/filter_owytac.png")
                .decoderFactory(SvgDecoder.Factory())
                .crossfade(true)
                .size(Size.ORIGINAL)
                .build(),
            contentDescription = "home",
            modifier = Modifier
                .height(20.dp)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation
                )
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null,
                    onClick = {
                        coroutineScope.launch {
                            setIsScaledDown(true)
                            delay(500)
                        }
                    }
                )
                .onGloballyPositioned { layoutCoordinates ->
                    imageHeight.value = with(density) { (layoutCoordinates.size.height).toDp() }
                    imageWidth.value = with(density) { (layoutCoordinates.size.height).toDp() }
                }
        )
    }
}
