package com.erichydev.nyumbakumi.homeComposables

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.erichydev.nyumbakumi.appComposables.PlotRatingStars
import com.erichydev.nyumbakumi.data.Plot
import com.erichydev.nyumbakumi.ui.theme.NormalTheme

@Composable
fun PlotOverview(
    plot: Plot,
    onPlotClick: () -> Unit,
    loadingAd: Boolean
) {
    Column(
        modifier = Modifier
            .background(Color.Black.copy(alpha = 0.2f), shape = RoundedCornerShape(10.dp))
            .clickable {
                onPlotClick()
            }
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(plot.plotBgPic ?: "file:///android_asset/mansion.svg")
                .decoderFactory(SvgDecoder.Factory())
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "home",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Column(
            modifier = Modifier.padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724772194/nyumba_kumi/icons/location_nerajg.png")
                                .decoderFactory(SvgDecoder.Factory())
                                .crossfade(true)
                                .build(),
                            contentDescription = "plot location",
                            modifier = Modifier
                                .size(15.dp)  // Optimized image size
                        )

                        NormalTheme {
                            Text(
                                text = plot.plotLocation,
                                modifier = Modifier
                                    .width(180.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724772506/nyumba_kumi/icons/room_pmestm.png")
                                .decoderFactory(SvgDecoder.Factory())
                                .crossfade(true)
                                .build(),
                            contentDescription = "home",
                            modifier = Modifier
                                .size(15.dp)  // Optimized image size
                        )

                        NormalTheme {
                            val roomTypes = remember(plot.plotSingle, plot.plotBedsitter, plot.plot1B, plot.plot2B, plot.plot3B) {
                                buildList {
                                    if (plot.plotSingle) add("Single Room")
                                    if (plot.plotBedsitter) add("Bedsitter")
                                    if (plot.plot1B) add("1 Bedroom")
                                    if (plot.plot2B) add("2 Bedroom")
                                    if (plot.plot3B) add("3 Bedroom")
                                }
                            }
                            val value = if (roomTypes.isNotEmpty()) {
                                roomTypes.joinToString(", ")
                            } else {
                                "No rooms specified"
                            }
                            Text(
                                text = value,
                                color = Color(0xFF8d99ae),
                                modifier = Modifier
                                    .width(180.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    PlotRatingStars(plot.plotRating)

                    if(plot.plotRating > 0)
                        NormalTheme {
                            Text(
                                text = "${plot.plotRating} from 1 rating",
                                color = Color(0xFF8d99ae)
                            )
                        }
                }
            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724845657/nyumba_kumi/icons/cash_xsnzje.png")
                            .decoderFactory(SvgDecoder.Factory())
                            .crossfade(true)
                            .build(),
                        contentDescription = "cash",
                        modifier = Modifier
                            .size(20.dp)
                    )

                    Text(
                        text = "Ksh.${"%,d".format(plot.plotPrice)}",
                        fontSize = 18.sp,
                        color = Color(0xFF24c87e),
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                }

                if(loadingAd)
                    Text(
                        text = "Loading Ad...",
                        color = Color.Red,
                        fontSize = 12.sp,
                    )
            }
        }
    }
}