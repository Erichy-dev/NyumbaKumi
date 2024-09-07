package com.erichydev.nyumbakumi.plotComposables

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun plotMapSearch(plotMap: String) {
    val context = LocalContext.current

    fun openGoogleMaps(location: String) {
        val encodedLocation = Uri.encode(location)
        val gmmIntentUri = Uri.parse("geo:0,0?q=$encodedLocation")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        try {
            Toast.makeText(context, "Map code copied to clipboard", Toast.LENGTH_SHORT).show()
            context.startActivity(mapIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Google Maps not installed", Toast.LENGTH_SHORT).show()
            // Google Maps app is not installed, open in Play Store
            val playStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.apps.maps"))
            context.startActivity(playStoreIntent)
        }
    }

    openGoogleMaps(plotMap)
}
