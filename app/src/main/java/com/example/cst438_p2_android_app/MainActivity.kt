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

private enum class AppScreen {
    LOGIN,
    HOME,
    RECIPE
}
class MainActivity : ComponentActivity() {
    //this variable is what is going to hold my helper method that I created ealier
    private lateinit var googleAuthManager: GoogleAuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        googleAuthManager = GoogleAuthManager(this)

        setContent {
            CST438_P2_Android_APPTheme {

                var currentScreen by remember {
                    mutableStateOf(AppScreen.LOGIN)
                }

                when (currentScreen) {
                    AppScreen.LOGIN -> {
                        Login(
                            onGoogleSignInClick = {
                                googleAuthManager.startSignIn()
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



    override fun onDestroy() {
        googleAuthManager.dispose()
        super.onDestroy()
    }

}