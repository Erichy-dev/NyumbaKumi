package com.erichydev.nyumbakumi

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowMetrics
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.erichydev.nyumbakumi.screens.PlotScreen
import com.erichydev.nyumbakumi.ui.navigation.Screens
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.activity.viewModels
import com.erichydev.nyumbakumi.homeComposables.HomeViewModel
import com.erichydev.nyumbakumi.homeComposables.UserViewModel
import com.erichydev.nyumbakumi.screens.Account
import com.erichydev.nyumbakumi.screens.Home
import com.erichydev.nyumbakumi.screens.SignIn
import com.erichydev.nyumbakumi.ui.theme.NormalTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.delay

const val WEB_CLIENT_ID = "964684700380-4qlqm0k352c94jqg70uaoc7el4kous9k.apps.googleusercontent.com"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private var mInterstitialAd: InterstitialAd? = null
    private var adId: String = "ca-app-pub-7987528218810169/6542312569"

    private val userDataViewModel: UserViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    private val adSize: AdSize
        get() {
            val displayMetrics = resources.displayMetrics
            val adWidthPixels =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val windowMetrics: WindowMetrics = this.windowManager.currentWindowMetrics
                    windowMetrics.bounds.width()
                } else {
                    displayMetrics.widthPixels
                }
            val density = displayMetrics.density
            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(this, adWidth)
        }

    private fun loadInterstitialAd(adStatus: (Boolean) -> Unit) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, adId, adRequest, object: InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(error: LoadAdError) {
                super.onAdFailedToLoad(error)
                mInterstitialAd = null;
                Log.i("AD_TAG", "onAdFailedToLoad: ${error.message}")
                adStatus(false)
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                super.onAdLoaded(interstitialAd)
                mInterstitialAd = interstitialAd
                Log.i("AD_TAG", "onAdLoaded: ")
                adStatus(true)
            }
        })
    }
    private fun showInterstitialAd(callback: () -> Unit, setAdStatus: (Boolean) -> Unit) {
        mInterstitialAd?.let { ad ->
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    Log.i("AD_TAG", "onAdDismissedFullScreenContent: ")
                    mInterstitialAd = null
                    callback()
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    Log.i("AD_TAG", "onAdImpression: ")
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    Log.i("AD_TAG", "onAdClicked: ")
                }
            }
            ad.show(this)
        } ?: kotlin.run {
//            Toast.makeText(this, "Fetching Ad", Toast.LENGTH_SHORT).show()
            setAdStatus(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            )
        )

        auth = Firebase.auth

        setContent {
            NormalTheme {
                var backPressedTime by remember { mutableLongStateOf(0L) }
                val context = LocalContext.current

                var adStatus by remember {
                    mutableStateOf(false)
                }
                StartAdBackgroundTask(adStatus, { adStatus = it }, { loadInterstitialAd(it) })

                BackHandler {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - backPressedTime < 2000) {
                        finish() // Close the app
                    } else {
                        backPressedTime = currentTime
                        Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
                    }
                }

                App(
                    auth = FirebaseAuth.getInstance(),
                    userDataViewModel,
                    homeViewModel,
                    adStatus,
                    setAdStatus = { adStatus = it },
                    loadInterstitialAd = { loadInterstitialAd(it) },
                    { showInterstitialAd(it) { adStatus = it } },
                )
            }
        }
    }
}

@Composable
fun App(
    auth: FirebaseAuth,
    userDataViewModel: UserViewModel,
    homeViewModel: HomeViewModel,
    adStatus: Boolean,
    setAdStatus: (Boolean) -> Unit,
    loadInterstitialAd: (adStatus: (Boolean) -> Unit) -> Unit,
    showInterstitialAd: (callback: () -> Unit) -> Unit,
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)

    val startDestination = if(auth.currentUser != null) {
        Screens.HomeScreen.route
    } else {
        Screens.SignInScreen.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screens.SignInScreen.route) {
            SignIn {
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(WEB_CLIENT_ID)
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()
                scope.launch {
                    try {
                        val result = credentialManager.getCredential(context, request)
                        val credential = result.credential
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        val googleIdToken = googleIdTokenCredential.idToken
                        val firebaseCredential =
                            GoogleAuthProvider.getCredential(googleIdToken, null)
                        auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    navController.navigate(Screens.HomeScreen.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                    }
                                } else {
                                    // Handle sign-in error
                                    Toast.makeText(
                                        context,
                                        "Sign-in failed: ${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        e.printStackTrace()
                    }
                }
            }
        }

        composable(Screens.HomeScreen.route) {
            Home(
                { plotId -> navController.navigate(Screens.PlotScreen.passPlotId(plotId)) },
                navController,
                auth.currentUser,
                userDataViewModel,
                homeViewModel,
                adStatus,
                setAdStatus,
                { loadInterstitialAd(it) },
                { showInterstitialAd(it) }
            )
        }

        composable(Screens.AccountScreen.route) {
            Account(
                onSignOutClick = {
                    navController.navigate(Screens.SignInScreen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                    auth.signOut()
                    scope.launch {
                        credentialManager.clearCredentialState(
                            ClearCredentialStateRequest()
                        )
                    }
                },
                auth.currentUser,
                userDataViewModel,
                navController
            )
        }

        composable(Screens.PlotScreen.route, arguments = listOf(
            navArgument("plotId") {
                type = NavType.StringType
            }
        )) {
            PlotScreen(
                auth.currentUser,
                userDataViewModel,
                navController
            )
        }
    }
}

@Composable
fun StartAdBackgroundTask(
    adStatus: Boolean,
    setAdStatus: (Boolean) -> Unit,
    loadInterstitialAd: (adStatus: (Boolean) -> Unit) -> Unit,
) {
    LaunchedEffect(adStatus) {
        println("adstatus value $adStatus")
        while(!adStatus) {
            println("adStatus : false ; no ads available. retrying ...")
            loadInterstitialAd { setAdStatus(it) }
            delay(1000)
        }
    }
}