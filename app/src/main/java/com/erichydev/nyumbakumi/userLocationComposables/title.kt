package com.erichydev.nyumbakumi.userLocationComposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun UserLocationTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Select Residence",
            fontFamily = FontFamily.Serif,
            fontSize = 30.sp,
            color = Color(0xFFE6E6E6),
            fontWeight = FontWeight.ExtraBold
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724748369/nyumba_kumi/icons/building_nverxn.png")
                .decoderFactory(SvgDecoder.Factory())
                .crossfade(true)
                .build(),
            contentDescription = "home",
            modifier = Modifier
                .height(35.dp)
        )
    }
}