package com.erichydev.nyumbakumi.plotComposables

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.erichydev.nyumbakumi.data.Plot
import com.erichydev.nyumbakumi.data.PlotOccupant
import com.erichydev.nyumbakumi.ui.theme.DescriptionTheme
import com.erichydev.nyumbakumi.ui.theme.NormalTheme
import com.erichydev.nyumbakumi.ui.theme.SubTitleTheme
import kotlinx.coroutines.delay

@Composable
fun PlotContacts(
    plotCaretakers: List<PlotOccupant>,
    plot: Plot
) {
    val context = LocalContext.current

    val clipboardManager: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    var copySvg by remember { mutableStateOf(false) }

    LaunchedEffect(copySvg) {
        if (copySvg) {
            delay(2000)
            copySvg = false
        }
    }

    fun toggleCopySvg(text: String) {
        copySvg = true
        val clip = ClipData.newPlainText("copied_map", text)
        clipboardManager.setPrimaryClip(clip)
    }
    val copyState = remember { mutableStateMapOf<String, Boolean>() }

    fun copyCaretakerNo(phone: String) {
        copyState[phone] = true
        val clip = ClipData.newPlainText("copied_phone_number", phone)
        clipboardManager.setPrimaryClip(clip)
    }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .height(250.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFF000000), shape = RoundedCornerShape(10.dp))
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                SubTitleTheme {
                    Text(text = "Caretakers", modifier = Modifier.padding(end = 20.dp))
                }
                LazyColumn(
                    modifier = Modifier
                        .height((plotCaretakers.size * 20).dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(plotCaretakers) { caretaker ->
                        val phone = caretaker.plotOccupantPhone
                        val isCopied = copyState[phone] ?: false

                        LaunchedEffect(isCopied) {
                            if (isCopied) {
                                delay(2000)
                                copyState[phone] = false
                            }
                        }

                        LazyRow(
                            modifier = Modifier.clickable { copyCaretakerNo(phone) },
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            item {
                                DescriptionTheme {
                                    Text(
                                        text = phone,
                                        textDecoration = TextDecoration.Underline,
                                        color = if (isCopied) Color(0xFFffd166) else Color(0xFFedf2f4)
                                    )
                                }
                            }
                            item {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724833567/nyumba_kumi/icons/click_dmmdcu.png")
                                        .decoderFactory(SvgDecoder.Factory())
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "home",
                                    modifier = Modifier
                                        .size(20.dp)
                                )
                            }
                            if(isCopied) {
                                item {
                                    plotPhoneNumber(phone)
                                }
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                SubTitleTheme {
                    Text(text = "Map", modifier = Modifier.padding(end = 20.dp))
                }

                if(plot.plotMap != null) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .clickable { toggleCopySvg(plot.plotMap ?: "") },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DescriptionTheme {
                            Text(
                                text = plot.plotMap ?: "",
                                textDecoration = TextDecoration.Underline,
                                color = if(copySvg) Color(0xFFffd166) else Color(0xFFedf2f4)
                            )
                        }

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724833567/nyumba_kumi/icons/click_dmmdcu.png")
                                .decoderFactory(SvgDecoder.Factory())
                                .crossfade(true)
                                .build(),
                            contentDescription = "home",
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                    if(copySvg) {
                        plotMapSearch(plotMap = plot.plotMap!!)
                    }
                }
            }
        }
        }
}

@Preview
@Composable
fun PreviewPlotContacts() {
    val plotCaretakers = listOf(
        PlotOccupant("1", "id", "eric", "nyaga", "Caretaker", "0712345678", "en@gmail.com")
    )
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
    PlotContacts(plotCaretakers, plot)
}