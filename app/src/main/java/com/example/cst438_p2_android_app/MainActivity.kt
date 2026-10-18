package com.example.cst438_p2_android_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.cst438_p2_android_app.auth.GoogleAuthManager
import com.example.cst438_p2_android_app.ui.home.HomeScreen
import com.example.cst438_p2_android_app.ui.home.Login
import com.example.cst438_p2_android_app.ui.home.RecipeScreen
import com.example.cst438_p2_android_app.ui.theme.CST438_P2_Android_APPTheme
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.activity.compose.BackHandler

private enum class AppScreen {
    LOGIN,
    HOME,
    RECIPE
}
class MainActivity : ComponentActivity() {
    //this variable is what is going to hold my helper method that I created ealier
    private lateinit var googleAuthManager: GoogleAuthManager
    private var authError by mutableStateOf<String?>(null)
    private var currentScreen by mutableStateOf(AppScreen.LOGIN)
    private var googleIdToken by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        googleAuthManager = GoogleAuthManager(this)

        setContent {
            CST438_P2_Android_APPTheme {
                var currentScreen by rememberSaveable {
                    mutableStateOf(AppScreen.HOME)
                }
                BackHandler(enabled = currentScreen == AppScreen.RECIPE) {
                    currentScreen = AppScreen.HOME
                }

                when (currentScreen) {
                    AppScreen.LOGIN -> {
                        Login(
                            onGoogleSignInClick = {
                                authError = null
                                googleAuthManager.startSignIn { message ->
                                    showAuthError(message)
                                }
                            },
                            authError = authError,
                            onRetry = {
                                authError = null
                                googleAuthManager.startSignIn { message ->
                                    showAuthError(message)
                                }
                            }
                        )
                    }

                    AppScreen.HOME -> {
                        HomeScreen(
                            onAddRecipeClick = {
                                currentScreen = AppScreen.RECIPE
                            },
                            onUpdateMealPlanClick = {
                                //I still plan to add this later
                            }
                        )
                    }

                    AppScreen.RECIPE -> {
                        RecipeScreen(
                            onBackClick = {
                                currentScreen = AppScreen.HOME
                            }
                        )
                    }
                }
            }
        }
    }

    /**
     * This function is going to recieve the redirect Intent after the google sing in
     */
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GoogleAuthManager.AUTHORIZATION_REQUEST_CODE) {
            if (data == null) {
                Log.e("GoogleAuth", "Google sign in returned no data ")
                showAuthError("Google sign in returned no response. Please try again.")
                return
            }
            //asking GoogleAuthManager to process teh response and exachange the authorization code for a token
            googleAuthManager.authorizationRespone(data) {
                idTokenResult, errorMessage ->
                if (idTokenResult != null) {
                    //our authentication worked
                    runOnUiThread {
                        googleIdToken = idTokenResult
                        currentScreen = AppScreen.HOME
                    }
                    Log.d("GoogleAuth", "Google sign in worked")

                } else {
                    Log.e("GoogleAuth", errorMessage ?: "Unknown Google sign in error")
                    showAuthError(errorMessage ?: "Google sign in failed. Please try again.")
                }
            }
        }
    }

    private fun showAuthError(message: String) {
        runOnUiThread {
            authError = message
        }
    }

    override fun onDestroy() {
        googleAuthManager.dispose()
        super.onDestroy()
    }

}
