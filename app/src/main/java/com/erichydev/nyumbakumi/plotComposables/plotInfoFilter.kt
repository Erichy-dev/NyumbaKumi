package com.erichydev.nyumbakumi.plotComposables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bottomBorder
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.erichydev.nyumbakumi.data.Plot
import com.erichydev.nyumbakumi.data.PlotInfoFilterSchema
import com.erichydev.nyumbakumi.data.PlotOccupant
import com.erichydev.nyumbakumi.ui.theme.SubTitleTheme
import com.google.firebase.auth.FirebaseUser

@Composable
fun PlotInfoFilter(
    plotCaretakers: List<PlotOccupant>,
    plot: Plot,
    user: FirebaseUser?
) {
    // State to track the selected box
    var selectedBox by remember { mutableStateOf("Contacts") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Define a list of box labels
            val boxLabels = listOf(
                PlotInfoFilterSchema("Contacts", "https://res.cloudinary.com/dgaqws2ux/image/upload/v1724832353/nyumba_kumi/icons/contacts_jzvqvb.png"),
                PlotInfoFilterSchema("Details", "https://res.cloudinary.com/dgaqws2ux/image/upload/v1724832353/nyumba_kumi/icons/structure_varxsj.png"),
                PlotInfoFilterSchema("Reviews", "https://res.cloudinary.com/dgaqws2ux/image/upload/v1724832433/nyumba_kumi/icons/rating_ste7fy.png")
            )

            // Loop through each label and create a box
            boxLabels.forEach { label ->
                val isSelected = selectedBox == label.infoFilter
                Column(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null,
                            onClick = { selectedBox = label.infoFilter }
                        ) // Update the state on click
                        .padding(horizontal = 2.dp)
                        .then(
                            if (isSelected) {
                                Modifier
                                    .bottomBorder(strokeWidth = 4.dp, color = Color(0xFF2da3e5))
                                    .padding(vertical = 10.dp)
                            } else {
                                Modifier
                            }
                        ),
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(label.svg)
                            .decoderFactory(SvgDecoder.Factory())
                            .crossfade(true)
                            .build(),
                        contentDescription = "home",
                        modifier = Modifier
                            .size(24.dp)
                    )

                    SubTitleTheme {
                        Text(
                            text = label.infoFilter,
                            color = if (isSelected) Color(0xFF118ab2) else Color.Unspecified
                        )
                    }
                }
            }
        }
        when(selectedBox){
            "Contacts" -> PlotContacts(plotCaretakers, plot)
            "Details" -> PlotDetails(plot)
            "Reviews" -> PlotReviews(plot, user)
        }
    }
}

@Preview
@Composable
fun PreviewPlotInfo() {
//    PlotInfoFilter()
}