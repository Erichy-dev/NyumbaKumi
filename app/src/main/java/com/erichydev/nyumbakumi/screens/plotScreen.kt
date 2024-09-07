package com.erichydev.nyumbakumi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.erichydev.nyumbakumi.data.Plot
import com.erichydev.nyumbakumi.data.PlotCaretakerResponse
import com.erichydev.nyumbakumi.data.PlotOccupant
import com.erichydev.nyumbakumi.data.PlotPic
import com.erichydev.nyumbakumi.data.PlotPicResponse
import com.erichydev.nyumbakumi.data.PlotResponse
import com.erichydev.nyumbakumi.appComposables.ScreenFoot
import com.erichydev.nyumbakumi.homeComposables.UserViewModel
import com.erichydev.nyumbakumi.plotComposables.PlotInfoFilter
import com.erichydev.nyumbakumi.plotComposables.PlotPics
import com.erichydev.nyumbakumi.api.getPlotData
import com.erichydev.nyumbakumi.api.plotCaretakersLiveData
import com.erichydev.nyumbakumi.api.plotLiveData
import com.erichydev.nyumbakumi.api.plotPicLiveData
import com.erichydev.nyumbakumi.plotComposables.PlotViewModel
import com.google.firebase.auth.FirebaseUser

@Composable
fun PlotScreen(
    currentUser: FirebaseUser?,
    userDataViewModel: UserViewModel,
    navController: NavController
) {
    val viewModel: PlotViewModel = hiltViewModel()

    var plot by remember {
        mutableStateOf(Plot(
            "", "", "", "", "", "", "", 0, null,
            plotSingle = false,
            plotBedsitter = false,
            plot1B = false,
            plot2B = false,
            plot3B = false,
            plotType = "",
            plotElectricity = "",
            plotWater = "",
            plotGarbage = "",
            plotSecurity = "",
            plotRating = 0,
            plotBgPic = "",
            plotUploadDate = ""
        ))
    }
    var plotPic by remember {
        mutableStateOf(listOf<PlotPic>())
    }
    var plotCaretakers by remember {
        mutableStateOf(listOf<PlotOccupant>())
    }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = Observer<PlotResponse> {
            plot = it.plot
        }
        plotLiveData.observe(lifecycleOwner, observer)

        val picObserver = Observer<PlotPicResponse> {
            plotPic = it.plotPics
        }
        plotPicLiveData.observe(lifecycleOwner, picObserver)

        val plotCaretakerObserver = Observer<PlotCaretakerResponse> {
            plotCaretakers = it.caretakers
        }
        plotCaretakersLiveData.observe(lifecycleOwner, plotCaretakerObserver)

        onDispose {
            plotLiveData.removeObserver(observer)
            plotPicLiveData.removeObserver(picObserver)
            plotCaretakersLiveData.removeObserver(plotCaretakerObserver)
        }
    }
    val plotId = viewModel.plotId.collectAsState()
    getPlotData(context, plotId.value)

    var imagesLoaded by remember {
        mutableIntStateOf(0)
    }

    Scaffold {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ){
//            if(plotPic.isEmpty() || imagesLoaded < plotPic.size) {
//                Loader()
//            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    PlotPics(
                        plotPic,
                        imagesLoaded,
                        setImagesLoaded = {images ->
                            imagesLoaded = images
                        }
                    )
                }

                item {
                    PlotInfoFilter(plotCaretakers, plot, currentUser)
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .zIndex(3f)
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null,
                        onClick = {}
                    )
                    .background(Color.Black)
            ) {
                ScreenFoot(
                    navController,
                    userDataViewModel,
                    currentUser
                )
            }
        }
    }
}