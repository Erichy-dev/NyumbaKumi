package com.erichydev.nyumbakumi.homeComposables.filters

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.erichydev.nyumbakumi.homeComposables.HomeViewModel
import com.erichydev.nyumbakumi.ui.theme.NormalTheme
import com.erichydev.nyumbakumi.ui.theme.SubTitleTheme

@Composable
fun PlotLocation(
    viewModel: HomeViewModel,
) {
    val gradientColors = listOf(
        Color(0xFFdadce5),
        Color(0xFF3477d9),
        Color(0xFF8bd0fe),
        Color(0xFF83cbab),
        Color(0xFFf1d874),
    )
    val gradientBrush = Brush.linearGradient(
        colors = gradientColors
    )

    val selectedLocationOption by viewModel.selectedLocationOption.observeAsState("Kiganjo")

    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724772194/nyumba_kumi/icons/location_nerajg.png")
                    .decoderFactory(SvgDecoder.Factory())
                    .crossfade(true)
                    .build(),
                contentDescription = "home",
                modifier = Modifier
                    .size(30.dp)
            )

            SubTitleTheme {
                Text(
                    text = "Location",
                    fontSize = 16.sp,
                    style = TextStyle(brush = gradientBrush)
                )
            }
        }

        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val locations = listOf("Kiganjo", "Makongeni", "Runda", "Weiteithie")

            items(locations) { location ->
                val isSelected = selectedLocationOption == location
                val brush = if (isSelected) gradientBrush else Brush.linearGradient(colors = listOf(Color.White, Color.White))

                Column(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null,
                            onClick = { viewModel.setSelectedLocationOption(location) }
                        )
                        .border(
                            width = 1.dp,
                            brush = brush,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(10.dp)
                ) {
                    NormalTheme {
                        Text(location)
                    }
                }
            }
        }
    }
}