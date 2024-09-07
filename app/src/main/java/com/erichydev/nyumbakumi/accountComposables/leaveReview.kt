package com.erichydev.nyumbakumi.accountComposables

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.erichydev.nyumbakumi.ui.theme.NormalTheme

@Composable
fun LeaveReview() {
    val gradientColors = listOf(
        Color(0xFF6cb4c4),
        Color(0xFF8d8da9),
        Color(0xFFa86b92),
        Color(0xFFc84376),
        Color(0xFFe22361),
    )

    // Create a linear gradient brush
    val gradientBrush = Brush.linearGradient(
        colors = gradientColors
    )
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(10.dp),
    ) {
        Column(
            modifier = Modifier
                .border(width = 1.dp, color = Color(0xFF2da3e5), shape = RoundedCornerShape(20.dp))
                .height(200.dp)
                .fillMaxWidth()
                .clickable {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.erichydev.nyumbakumi&reviewId=0")
                    )
                    context.startActivity(intent)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NormalTheme {
                Text(
                    text = "Leave a review on Play Store",
                    fontSize = 20.sp,
                    style = TextStyle(
                        brush = gradientBrush
                    )
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724833567/nyumba_kumi/icons/rate_lvukqw.png")
                    .decoderFactory(SvgDecoder.Factory())
                    .crossfade(true)
                    .build(),
                contentDescription = "home",
                modifier = Modifier
                    .height(60.dp)
            )
        }
    }
}