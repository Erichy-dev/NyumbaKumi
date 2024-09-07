package com.erichydev.nyumbakumi.plotComposables

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString

@Composable
fun plotPhoneNumber(phoneNumber: String) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    fun copyToClipboard(number: String) {
        clipboardManager.setText(AnnotatedString(number))
        Toast.makeText(context, "Phone number copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    fun openDialer(number: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$number")
        }
        try {
            context.startActivity(dialIntent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open dialer app", Toast.LENGTH_SHORT).show()
        }
    }

    // Trigger the actions
    copyToClipboard(phoneNumber)
    openDialer(phoneNumber)
}
