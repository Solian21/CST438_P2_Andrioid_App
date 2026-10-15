package com.example.cst438_p2_android_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cst438_p2_android_app.ui.theme.CST438_P2_Android_APPTheme
import com.example.cst438_p2_android_app.ui.home.HomeScreen
import com.example.cst438_p2_android_app.ui.home.Login
import com.example.cst438_p2_android_app.ui.home.RecipeScreen
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.activity.compose.BackHandler

private enum class AppScreen {
    HOME,
    RECIPE
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CST438_P2_Android_APPTheme {
                var currentScreen by rememberSaveable {
                    mutableStateOf(AppScreen.HOME)
                }
                BackHandler(enabled = currentScreen == AppScreen.RECIPE) {
                    currentScreen = AppScreen.HOME
                }

                when (currentScreen) {
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
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CST438_P2_Android_APPTheme {
        Greeting("Android")
    }
}