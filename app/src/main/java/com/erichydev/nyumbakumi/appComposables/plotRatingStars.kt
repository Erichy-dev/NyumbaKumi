package com.erichydev.nyumbakumi.appComposables

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.erichydev.nyumbakumi.data.Plot

@Composable
fun PlotRatingStars(
    plotRate: Int
) {
    LazyRow {
        items(5) { index ->
            val starImage = if (index < plotRate) {
                "https://res.cloudinary.com/dgaqws2ux/image/upload/v1724773932/nyumba_kumi/icons/star_nfp86t.svg"
            } else {
                "https://res.cloudinary.com/dgaqws2ux/image/upload/v1724773894/nyumba_kumi/icons/un-star_njnyfb.svg"
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(starImage)
                    .decoderFactory(SvgDecoder.Factory())
                    .crossfade(true)
                    .build(),
                contentDescription = if (index < plotRate) "rated star" else "unrated star",
                modifier = Modifier
                    .size(15.dp)
            )
        }
    }
}