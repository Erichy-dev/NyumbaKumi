package com.erichydev.nyumbakumi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.erichydev.nyumbakumi.homeComposables.HomeViewModel
import com.erichydev.nyumbakumi.homeComposables.PlotOverview
import com.erichydev.nyumbakumi.appComposables.ScreenFoot
import com.erichydev.nyumbakumi.homeComposables.FilterModalSheet
import com.erichydev.nyumbakumi.homeComposables.TopNavbar
import com.erichydev.nyumbakumi.homeComposables.UserViewModel
import com.erichydev.nyumbakumi.ui.navigation.Screens
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    onPlotClick: (plotId: String) -> Unit,
    navController: NavController,
    currentUser: FirebaseUser?,
    userDataViewModel: UserViewModel,
    viewModel: HomeViewModel,
    adStatus: Boolean,
    setAdStatus: (Boolean) -> Unit,
    loadInterstitialAd: (adStatus: (Boolean) -> Unit) -> Unit,
    showInterstitialAd: (callback: () -> Unit) -> Unit
) {
    val context = LocalContext.current

    val selectedLocationOption by viewModel.selectedLocationOption.observeAsState("Kiganjo")
    val plots by viewModel.plots.observeAsState(emptyList())
    val originalPlots by viewModel.originalPlots.observeAsState(emptyList())

    val userData by userDataViewModel.userData.observeAsState(null)

    LaunchedEffect(Unit) {
        if(originalPlots.isEmpty()){
            viewModel.fetchPlotsByAddress(context, selectedLocationOption)
        }
    }

    LaunchedEffect(originalPlots) {
        viewModel.priceUnitFilter()
    }

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    var plotId by remember { mutableStateOf("") }
    var displayAd by remember { mutableStateOf(false) }

    if(!displayAd) {
        Scaffold{
            if(showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    FilterModalSheet(
                        viewModel,
                        filterPlots = {
                            showBottomSheet = false
                        }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ){
                LazyColumn(
                    modifier = Modifier
                        .padding(4.dp)
                        .padding(bottom = 65.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(25.dp)
                ) {
                    item {
                        TopNavbar(
                            isScaledDown = showBottomSheet,
                            setIsScaledDown = {showSheet ->
                                showBottomSheet = showSheet
                            }
                        )
                    }

                    items(plots, key = { plot -> plot.plotNumber }) { plot ->
                        PlotOverview(
                            plot = plot,
                            onPlotClick = {
                                plotId = plot.plotNumber
                                userDataViewModel.updateUserData(
                                    context,
                                    currentUser?.displayName ?: "",
                                    currentUser?.email ?: "",
                                    userData!!.views + 1,
                                    null
                                )
                                if(!adStatus) {
                                    displayAd = false
                                    loadInterstitialAd {
                                        setAdStatus(it)
                                    }
                                    onPlotClick(plot.plotNumber)
                                } else {
                                    displayAd = true
                                }
                            },
                            displayAd
                        )
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
    } else {
        // ad display
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            showInterstitialAd { onPlotClick(plotId) }
        }
    }
}