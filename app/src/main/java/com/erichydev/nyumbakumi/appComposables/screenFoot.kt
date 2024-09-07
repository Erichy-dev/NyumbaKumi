package com.erichydev.nyumbakumi.appComposables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.erichydev.nyumbakumi.homeComposables.UserViewModel
import com.google.firebase.auth.FirebaseUser

@Composable
fun ScreenFoot(
    navController: NavController,
    userDataViewModel: UserViewModel,
    currentUser: FirebaseUser?,
) {

    val context = LocalContext.current
    LaunchedEffect(currentUser) {
        if (currentUser?.displayName != null) {
            userDataViewModel.updateUserData(
                context,
                currentUser.displayName ?: "",
                currentUser.email ?: "",
                null,
                null
            )
        }
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp, bottom = 5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    navController.navigate("home")
                },
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724822114/nyumba_kumi/icons/home_auvrbk.png")
                    .decoderFactory(SvgDecoder.Factory())
                    .crossfade(true)
                    .build(),
                contentDescription = "plot location",
                modifier = Modifier
                    .size(30.dp)
            )

            Text(
                text = "Home",
                fontSize = 12.sp,
                color = Color(0xFF8d99ae),
            )
        }

        Column(
            modifier = Modifier
                .clickable {
                    navController.navigate("account")
                },
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724775841/nyumba_kumi/icons/account_u0ntuc.png")
                    .decoderFactory(SvgDecoder.Factory())
                    .crossfade(true)
                    .build(),
                contentDescription = "plot location",
                modifier = Modifier
                    .size(30.dp)
            )

            Text(
                text = currentUser?.displayName ?:  "Account",
                fontSize = 12.sp,
                color = Color(0xFF8d99ae),
                modifier = Modifier
                    .width(50.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}