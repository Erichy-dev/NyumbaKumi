package com.erichydev.nyumbakumi.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.erichydev.nyumbakumi.accountComposables.LeaveReview
import com.erichydev.nyumbakumi.accountComposables.UserName
import com.erichydev.nyumbakumi.accountComposables.WelcomeUser
import com.erichydev.nyumbakumi.appComposables.ScreenFoot
import com.erichydev.nyumbakumi.data.UserData
import com.erichydev.nyumbakumi.homeComposables.UserViewModel
import com.erichydev.nyumbakumi.ui.theme.DescriptionTheme
import com.erichydev.nyumbakumi.ui.theme.LinkText
import com.erichydev.nyumbakumi.ui.theme.NormalTheme
import com.erichydev.nyumbakumi.ui.theme.TitleTheme
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Account(
    onSignOutClick: () -> Unit,
    currentUser: FirebaseUser?,
    userDataViewModel: UserViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    Scaffold {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ){
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(50.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable { onSignOutClick() },
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724864804/nyumba_kumi/icons/log_out_ltehr2.png")
                                    .decoderFactory(SvgDecoder.Factory())
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "home",
                                modifier = Modifier
                                    .height(60.dp)
                            )

                            NormalTheme {
                                Text(
                                    text = "LOG OUT",
                                )
                            }
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        WelcomeUser()

                        UserName(currentUser)

                        DescriptionTheme {
                            Text(
                                text = currentUser?.email ?: "",
                                fontSize = 12.sp,
                                color = Color(0xFF929292)
                            )
                        }
                    }
                }

                item {
                    LeaveReview()
                }

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LinkText {
                            Text(
                                text = "Privacy Policy",
                                modifier = Modifier
                                    .clickable {
                                        val intent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://www.nyumbakumi.net/privacyPolicy")
                                        )
                                        context.startActivity(intent)
                                    }
                                    .padding(10.dp),
                            )
                        }
                    }
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