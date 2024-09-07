package com.erichydev.nyumbakumi.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.erichydev.nyumbakumi.signInComposables.AnimatedButton

@Composable
fun SignIn(
    onSignIn: () -> Unit,
) {
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels

    Scaffold {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724698264/nyumba_kumi/zvg2jgtwqqyrol1baz3w.jpg")
                    .crossfade(true)
                    .build(),
                contentDescription = "nyumba kumi",
                modifier = Modifier
                    .width(screenWidth.dp)
                    .height(screenHeight.dp)
                    .zIndex(-1f) // Low z-index
                    .align(Alignment.TopCenter),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Text(
                        text = "Find Your Dream Home Today!",
                        fontFamily = FontFamily.Serif,
                        fontSize = 35.sp,
                        color = Color(0xFFE6E6E6),
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "Simplifying Your Rental Search In Kenya",
                        fontSize = 16.sp,
                        color = Color(0xFFC0C0C0),
                        fontWeight = FontWeight.Normal
                    )

                    AnimatedButton(
                        func = onSignIn,
                        textContent = "Let's Go!"
                    )
                }
            }
        }
    }
}