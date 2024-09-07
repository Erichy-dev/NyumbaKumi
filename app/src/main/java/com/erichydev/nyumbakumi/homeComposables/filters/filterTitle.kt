package com.erichydev.nyumbakumi.homeComposables.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.erichydev.nyumbakumi.ui.theme.NormalTheme

@Composable
fun FilterTitle(){
    val gradientColors = listOf(
        Color(0xFFbf73e6),
        Color(0xFF40aee9),
        Color(0xFFe55a8b),
        Color(0xFF679ceb),
        Color(0xFFf0536a),
    )

    // Create a linear gradient brush
    val gradientBrush = Brush.linearGradient(
        colors = gradientColors
    )

    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724751469/nyumba_kumi/icons/filter_owytac.png")
                .decoderFactory(SvgDecoder.Factory())
                .crossfade(true)
                .build(),
            contentDescription = "home",
            modifier = Modifier
                .size(20.dp)
        )

        NormalTheme {
            Text(
                text = "Filters",
                modifier = Modifier.width(80.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily.Cursive,
                fontSize = 30.sp,
                style = TextStyle(
                    brush = gradientBrush
                )
            )
        }
    }
}