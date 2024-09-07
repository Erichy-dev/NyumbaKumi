package com.erichydev.nyumbakumi.ui.navigation

sealed class Screens(val route: String) {
    data object MarketingFormScreen : Screens("marketingForm")

    data object SignInScreen : Screens("signIn")

    data object HomeScreen : Screens("home")

    data object AccountScreen : Screens("account")

    data object PlotScreen: Screens("plot/{plotId}"){
        fun passPlotId(plotId: String):String {
            return "plot/$plotId"
        }
    }
}