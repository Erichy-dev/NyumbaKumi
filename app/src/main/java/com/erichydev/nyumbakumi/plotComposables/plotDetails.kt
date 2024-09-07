package com.erichydev.nyumbakumi.plotComposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.erichydev.nyumbakumi.data.Plot
import com.erichydev.nyumbakumi.ui.theme.DescriptionTheme
import com.erichydev.nyumbakumi.ui.theme.NormalTheme
import com.erichydev.nyumbakumi.ui.theme.SubTitleTheme
import com.erichydev.nyumbakumi.ui.theme.TitleTheme

@Composable
fun PlotDetails(
    plot: Plot
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 100.dp, top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724838795/nyumba_kumi/icons/money-bag_ryxmai.png")
                    .decoderFactory(SvgDecoder.Factory())
                    .crossfade(true)
                    .build(),
                contentDescription = "home",
                modifier = Modifier
                    .size(25.dp)
            )

            SubTitleTheme {
                Text(text = "Plot Pricing")
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .background(Color(0xFF000000), shape = RoundedCornerShape(8.dp))
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                NormalTheme {
                    Text(text = "Price:", modifier = Modifier.padding(end = 20.dp))
                }
                DescriptionTheme {
                    Text(
                        text = "Ksh. ${"%,d".format(plot.plotPrice)}",
                        color = Color(0xFF24c87e)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                NormalTheme {
                    Text(text = "Price-description:", modifier = Modifier.padding(end = 20.dp))
                }
                DescriptionTheme {
                    plot.plotPriceDescription?.let { det -> Text(text = det) }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724838668/nyumba_kumi/icons/storey_bisjbn.png")
                    .decoderFactory(SvgDecoder.Factory())
                    .crossfade(true)
                    .build(),
                contentDescription = "plot structure",
                modifier = Modifier
                    .size(40.dp)
            )

            SubTitleTheme {
                Text(text = "Plot Structure")
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .background(Color(0xFF000000), shape = RoundedCornerShape(8.dp))
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                NormalTheme {
                    Text(text = "Type:", modifier = Modifier.padding(end = 20.dp))
                }
                DescriptionTheme {
                    Text(text = plot.plotType)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                NormalTheme {
                    Text(text = "Rooms:", modifier = Modifier.padding(end = 20.dp))
                }
                DescriptionTheme {
                    val roomTypes = buildList {
                        if (plot.plotSingle) add("Single Room")
                        if (plot.plotBedsitter) add("Bedsitter")
                        if (plot.plot1B) add("1 Bedroom")
                        if (plot.plot2B) add("2 Bedroom")
                        if (plot.plot3B) add("3 Bedroom")
                    }
                    val value = if (roomTypes.isNotEmpty()) {
                        roomTypes.joinToString(", ")
                    } else {
                        "No rooms specified"
                    }
                    Text(text = value)
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724838669/nyumba_kumi/icons/utility_iiqwav.png")
                    .decoderFactory(SvgDecoder.Factory())
                    .crossfade(true)
                    .build(),
                contentDescription = "plot structure",
                modifier = Modifier
                    .size(30.dp)
            )

            SubTitleTheme {
                Text(text = "Plot Ammenities")
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .background(Color(0xFF000000), shape = RoundedCornerShape(8.dp))
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                NormalTheme {
                    Text(text = "Electricity:", modifier = Modifier.padding(end = 20.dp))
                }
                DescriptionTheme {
                    Text(text = plot.plotElectricity)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                NormalTheme {
                    Text(text = "Water:", modifier = Modifier.padding(end = 20.dp))
                }
                DescriptionTheme {
                    Text(text = plot.plotWater)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                NormalTheme {
                    Text(text = "Garbage:", modifier = Modifier.padding(end = 20.dp))
                }
                DescriptionTheme {
                    Text(text = plot.plotGarbage)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                NormalTheme {
                    Text(text = "Security:", modifier = Modifier.padding(end = 20.dp))
                }
                DescriptionTheme {
                    Text(text = plot.plotSecurity)
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724772194/nyumba_kumi/icons/location_nerajg.png")
                    .decoderFactory(SvgDecoder.Factory())
                    .crossfade(true)
                    .build(),
                contentDescription = "plot structure",
                modifier = Modifier
                    .size(30.dp)
            )

            SubTitleTheme {
                Text(text = "Plot Location")
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .background(Color(0xFF000000), shape = RoundedCornerShape(8.dp))
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                NormalTheme {
                    Text(text = "Address:", modifier = Modifier.padding(end = 20.dp))
                }
                DescriptionTheme {
                    Text(text = plot.plotAddress)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                NormalTheme {
                    Text(text = "Location:", modifier = Modifier.padding(end = 20.dp))
                }
                DescriptionTheme {
                    Text(text = plot.plotLocation)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPlotDetails() {
    val plot = Plot(
        "",
        "",
        "",
        "",
        "",
        "ajklsd",
        "",
        4000,
        "cool plot",
        false,
        false,
        true,
        false,
        false,
        "multi-storey",
        "multi-storey",
        "",
        "",
        "",
        4,
        "",
        "",
    )
    PlotDetails(
        plot
    )
}