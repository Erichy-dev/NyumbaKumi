package com.erichydev.nyumbakumi.plotComposables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest


@Composable
fun UserRating(userRate: Int, setUserRate: (Int) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(16.dp)
    ) {
        items(5) { index ->
            // Choose the appropriate star image based on the current rating
            val starImage = if (index < userRate) {
                "https://res.cloudinary.com/dgaqws2ux/image/upload/v1724773932/nyumba_kumi/icons/star_nfp86t.svg"
            } else {
                "https://res.cloudinary.com/dgaqws2ux/image/upload/v1724773894/nyumba_kumi/icons/un-star_njnyfb.svg"
            }

            // Display the star image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(starImage)
                    .decoderFactory(SvgDecoder.Factory())
                    .crossfade(true)
                    .build(),
                contentDescription = if (index < userRate) "Rated star" else "Unrated star",
                modifier = Modifier
                    .size(30.dp)
                    .padding(4.dp)
                    .clickable {
                        // Update the rating when a star is clicked
                        setUserRate(index + 1)
                    }
            )
        }
    }
}

@Preview
@Composable
fun PreviewUserRating() {
//    UserRating()
}