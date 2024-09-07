package com.erichydev.nyumbakumi.plotComposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.erichydev.nyumbakumi.data.PlotPic

@Composable
fun PlotPics(
    plotPic: List<PlotPic>,
    imagesLoaded: Int,
    setImagesLoaded: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(plotPic){pic ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pic.plotPic)
                    .crossfade(true)
                    .listener(
                        onSuccess = { _, _ ->
                            setImagesLoaded(imagesLoaded+1)
                        }
                    )
                    .build(),
                contentDescription = "nyumba kumi",
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(350.dp)
            )
        }
    }
}