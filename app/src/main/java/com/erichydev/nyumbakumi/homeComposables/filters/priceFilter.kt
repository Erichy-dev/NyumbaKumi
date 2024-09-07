package com.erichydev.nyumbakumi.homeComposables.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.erichydev.nyumbakumi.data.Plot
import com.erichydev.nyumbakumi.homeComposables.HomeViewModel
import com.erichydev.nyumbakumi.ui.theme.DescriptionTheme
import com.erichydev.nyumbakumi.ui.theme.NormalTheme
import com.erichydev.nyumbakumi.ui.theme.SubTitleTheme

@Composable
fun PriceFilter(
    viewModel: HomeViewModel,
) {
    val gradientColors = listOf(
        Color(0xFFfefffe),
        Color(0xFFe3e3db),
        Color(0xFF5daa6c),
        Color(0xFFb7dc98),
        Color(0xFFb5d8b5),
    )
    val gradientBrush = Brush.linearGradient(
        colors = gradientColors
    )

    val minPrice by viewModel.minPrice.observeAsState(0f)
    val maxPrice by viewModel.maxPrice.observeAsState(15000f)

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724845657/nyumba_kumi/icons/cash_xsnzje.png")
                    .decoderFactory(SvgDecoder.Factory())
                    .crossfade(true)
                    .build(),
                contentDescription = "home",
                modifier = Modifier
                    .size(30.dp)
            )

            SubTitleTheme {
                Text(
                    text = "Price Range",
                    fontSize = 16.sp,
                    style = TextStyle(brush = gradientBrush)
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalTheme {
                        Text("Min: ")
                    }
                    DescriptionTheme {
                        Text("Ksh.${minPrice.toInt()}")
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalTheme {
                        Text("Max: ")
                    }
                    DescriptionTheme {
                        Text("Ksh.${maxPrice.toInt()}")
                    }
                }
            }

            RangeSlider(
                value = minPrice..maxPrice,
                onValueChange = { range ->
                    viewModel.setMinPrice(range.start)
                    viewModel.setMaxPrice(range.endInclusive)
                },
                valueRange = 0f..15000f,
                steps = ((15000f - 0f) / 500).toInt().coerceAtLeast(1) - 1,
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFF5daa6c),
                    activeTrackColor = Color.Transparent,
                    inactiveTickColor = Color(0xFF5daa6c)
                ),
            )
        }
    }
}