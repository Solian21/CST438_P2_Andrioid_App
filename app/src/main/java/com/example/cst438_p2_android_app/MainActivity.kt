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
import androidx.lifecycle.lifecycleScope
import com.example.cst438_p2_android_app.network.ApiClient
import kotlinx.coroutines.launch
import com.example.cst438_p2_android_app.network.MealPlanResponse
import com.example.cst438_p2_android_app.ui.home.CreateMealPlanScreen
import com.example.cst438_p2_android_app.network.MealPlanCreate
import com.example.cst438_p2_android_app.ui.home.MealPlanDetailScreen
import com.example.cst438_p2_android_app.network.MealPlanPatch
import com.example.cst438_p2_android_app.ui.home.UpdateMealPlanScreen
import com.example.cst438_p2_android_app.network.UserResponse
import com.example.cst438_p2_android_app.network.UserRoleUpdate
import com.example.cst438_p2_android_app.ui.home.AdminScreen
import com.example.cst438_p2_android_app.network.RecipeResponse
import com.example.cst438_p2_android_app.ui.home.RecipeDetailScreen
import com.example.cst438_p2_android_app.network.IngredientResponse
import com.example.cst438_p2_android_app.network.IngredientCreate
import com.example.cst438_p2_android_app.network.RecipeCreate
import com.example.cst438_p2_android_app.ui.home.CreateRecipeScreen
import com.example.cst438_p2_android_app.ui.home.CreateIngredientScreen

private enum class AppScreen {
    LOGIN,
    HOME,
    CREATE_INGREDIENT,
    CREATE_RECIPE,
    RECIPE,
    RECIPE_DETAIL,
    CREATE_MEAL_PLAN,
    MEAL_PLAN_DETAIL,
    UPDATE_MEAL_PLAN,
    ADMIN
}
class MainActivity : ComponentActivity() {
    //this variable is what is going to hold my helper method that I created ealier
    private lateinit var googleAuthManager: GoogleAuthManager
    private var authError by mutableStateOf<String?>(null)
    private var currentScreen by mutableStateOf(AppScreen.LOGIN)
    private var googleIdToken by mutableStateOf<String?>(null)
    private var userDisplayName by mutableStateOf("")

    private var mealPlans by mutableStateOf<List<MealPlanResponse>>(emptyList())
    private var selectedMealPlan by mutableStateOf<MealPlanResponse?>(null)
    private var currentUserRole by mutableStateOf("")
    private var adminUsers by mutableStateOf<List<UserResponse>>(emptyList())
    private var recipes by mutableStateOf<List<RecipeResponse>>(emptyList())
    private var selectedRecipe by mutableStateOf<RecipeResponse?>(null)
    private var ingredients by mutableStateOf<List<IngredientResponse>>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        googleAuthManager = GoogleAuthManager(this)

        setContent {
            CST438_P2_Android_APPTheme {
                BackHandler(enabled = currentScreen == AppScreen.CREATE_INGREDIENT || currentScreen == AppScreen.CREATE_RECIPE || currentScreen == AppScreen.RECIPE || currentScreen == AppScreen.RECIPE_DETAIL || currentScreen == AppScreen.CREATE_MEAL_PLAN || currentScreen == AppScreen.MEAL_PLAN_DETAIL || currentScreen == AppScreen.UPDATE_MEAL_PLAN || currentScreen == AppScreen.ADMIN) {
                    currentScreen = when (currentScreen) {
                        AppScreen.RECIPE_DETAIL -> AppScreen.RECIPE
                        else -> AppScreen.HOME
                    }
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
                            userName = userDisplayName,
                            mealPlans = mealPlans,
                            onAddRecipeClick = {
                                loadIngredients()
                            },
                            onAddIngredientClick = {
                                currentScreen = AppScreen.CREATE_INGREDIENT
                            },
                            onViewRecipesClick = {
                                loadRecipes()
                            },
                            onUpdateMealPlanClick = {
                                currentScreen = AppScreen.CREATE_MEAL_PLAN
                            },
                            onMealPlanClick = { mealPlanId ->
                                loadMealPlan(mealPlanId)

                            },
                            onLogoutClick = {
                                logout()
                            },
                            onDeleteAccountClick = {
                                deleteAccount()
                            },
                            isAdmin = currentUserRole  == "ADMIN",
                            onAdminClick = {
                                loadUsers()
                            }
                        )
                    }
                    AppScreen.CREATE_INGREDIENT -> {
                        CreateIngredientScreen(
                            onBackClick = {
                                currentScreen = AppScreen.HOME
                            },
                            onCreateClick = { request ->
                                createIngredient(request)
                            }
                        )
                    }
                    AppScreen.CREATE_RECIPE -> {
                        CreateRecipeScreen(
                            ingredients = ingredients,
                            onBackClick = {
                                currentScreen = AppScreen.HOME
                            },
                            onCreateClick = { request ->
                                createRecipe(request)
                            }
                        )
                    }

                    AppScreen.RECIPE -> {
                        RecipeScreen(
                            recipes = recipes,
                            onBackClick = {
                                currentScreen = AppScreen.HOME
                            },
                            onRefreshClick = {
                                loadRecipes()
                            },
                            onRecipeClick = { recipeId ->
                                loadRecipe(recipeId)
                            }
                        )
                    }
                    AppScreen.RECIPE_DETAIL -> {
                        selectedRecipe?.let { recipe ->
                            RecipeDetailScreen(
                                recipe = recipe,
                                onBackClick = {
                                    currentScreen = AppScreen.RECIPE
                                }
                            )
                        }
                    }
                    AppScreen.CREATE_MEAL_PLAN -> {
                        CreateMealPlanScreen(
                            onBackClick = {
                                currentScreen = AppScreen.HOME
                            },
                            onCreateClick = { weekStart ->
                                createMealPlan(weekStart)

                            }
                        )
                    }
                    AppScreen.MEAL_PLAN_DETAIL -> {
                        selectedMealPlan?.let { mealPlan ->
                            MealPlanDetailScreen(
                                mealPlan = mealPlan,
                                onBackClick = {
                                    currentScreen = AppScreen.HOME
                                },
                                onUpdateClick = {
                                    currentScreen = AppScreen.UPDATE_MEAL_PLAN
                                },
                                onDeleteClick = {
                                    deleteMealPlan(mealPlan.id)
                                }
                            )
                        }
                    }
                    AppScreen.UPDATE_MEAL_PLAN -> {
                        selectedMealPlan?.let { mealPlan ->
                            UpdateMealPlanScreen(
                                currentWeekStart = mealPlan.weekStart,
                                onBackClick = {
                                    currentScreen = AppScreen.MEAL_PLAN_DETAIL
                                },
                                onUpdateClick = { weekStart ->
                                    updateMealPlan(
                                        mealPlanId = mealPlan.id,
                                        weekStart = weekStart
                                    )
                                }
                            )
                        }
                    }
                    AppScreen.ADMIN -> {
                        AdminScreen(
                            users = adminUsers,
                            onBackClick = {
                                currentScreen = AppScreen.HOME
                            },
                            onRefreshClick = {
                                loadUsers()
                            },
                            onChangeRoleClick = { user ->
                                changeUserRole(user)
                            },
                            onDeleteUserClick = { user ->
                                deleteUser(user)
                            }
                        )
                    }
                }
            }
        }
    }
    /**
     * This makes the app wait for the GET /api/v1/user/me before opening the home screen
     */
    private fun loadCurrentUser(idToken: String) {
        lifecycleScope.launch {
            try {
                val authorizationHeader  = "Bearer $idToken"


                val user = ApiClient.apiService.getCurrentUser(authorizationHeader)
                val mealPlanResponse = ApiClient.apiService.getMealPlans(authorizationHeader)

                userDisplayName = user.displayName ?: user.email
                currentUserRole = user.role
                mealPlans = mealPlanResponse.content
                currentScreen = AppScreen.HOME

            } catch (e: Exception) {
                Log.e("Api", "Could not load account data", e)
                showAuthError("Could not connect to your account. Please try again.")
            }
        }
    }

    /**
     * this loads our recipes
     */
    private fun loadRecipes() {
        val idToken = googleIdToken

        if (idToken == null) {
            showAuthError("You must be signed in to view recipes.")
            currentScreen = AppScreen.LOGIN
            return
        }

        lifecycleScope.launch {
            try {
                val authorizationHeader = "Bearer $idToken"

                val response = ApiClient.apiService.getRecipes(
                    authorization = authorizationHeader
                )

                recipes = response.content
                currentScreen = AppScreen.RECIPE

            } catch (e: Exception) {
                Log.e("Api", "Could not load recipes", e)
                showAuthError("Could not load recipes.")
            }
        }
    }


    /**
     * this tells our API to create the recipe
     */
    private fun createRecipe(request: RecipeCreate) {
        val idToken = googleIdToken

        if (idToken == null) {
            showAuthError("You must be signed in to create a recipe.")
            currentScreen = AppScreen.LOGIN
            return
        }

        lifecycleScope.launch {
            try {
                val authorizationHeader = "Bearer $idToken"

                ApiClient.apiService.createRecipe(
                    authorization = authorizationHeader,
                    request = request
                )

                loadRecipes()

            } catch (e: Exception) {
                Log.e("Api", "Could not create recipe", e)
                showAuthError("Could not create the recipe.")
            }
        }
    }

    /**
     * This loads in our ingredient
     */
    private fun loadIngredients() {
        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.getIngredients()
                ingredients = response.content
                currentScreen = AppScreen.CREATE_RECIPE

            } catch (e: Exception) {
                Log.e("Api", "Could not load ingredients", e)
                showAuthError("Could not load ingredients.")
            }
        }
    }

    private fun createIngredient(request: IngredientCreate) {
        val idToken = googleIdToken

        if (idToken == null) {
            showAuthError("You must be signed in to create an ingredient.")
            currentScreen = AppScreen.LOGIN
            return
        }

        lifecycleScope.launch {
            try {
                val authorizationHeader = "Bearer $idToken"

                ApiClient.apiService.createIngredient(
                    authorization = authorizationHeader,
                    request = request
                )

                currentScreen = AppScreen.HOME

            } catch (e: Exception) {
                Log.e("Api", "Could not create ingredient", e)
                showAuthError("Could not create the ingredient.")
            }
        }
    }


    /**
     * loading recipe from recipe id
     */
    private fun loadRecipe(recipeId: Long) {
        val idToken = googleIdToken

        if (idToken == null) {
            showAuthError("You must be signed in to view a recipe.")
            currentScreen = AppScreen.LOGIN
            return
        }

        lifecycleScope.launch {
            try {
                val authorizationHeader = "Bearer $idToken"

                selectedRecipe = ApiClient.apiService.getRecipe(
                    authorization = authorizationHeader,
                    recipeId = recipeId
                )

                currentScreen = AppScreen.RECIPE_DETAIL

            } catch (e: Exception) {
                Log.e("Api", "Could not load recipe", e)
                showAuthError("Could not load the recipe.")
            }
        }
    }

    /**
     * this function creates the meal plan
     */
    private fun createMealPlan(weekStart: String) {
        val idToken = googleIdToken

        if (idToken == null) {
            showAuthError("You must be signed in to create a meal plan.")
            currentScreen = AppScreen.LOGIN
            return
        }

        lifecycleScope.launch {
            try {
                val authorizationHeader = "Bearer $idToken"

                ApiClient.apiService.createMealPlan(
                    authorization = authorizationHeader,
                    request = MealPlanCreate(weekStart)
                )

                val updatedMealPlanResponse =
                    ApiClient.apiService.getMealPlans(authorizationHeader)

                mealPlans = updatedMealPlanResponse.content
                currentScreen = AppScreen.HOME

            } catch (e: Exception) {
                Log.e("Api", "Could not create meal plan", e)
                showAuthError("Could not create the meal plan. Please try again.")
            }
        }
    }

    /**
     * This loads a specific meal plan form the API
     */
    private fun loadMealPlan(mealPlanId: Long) {
        val idToken = googleIdToken

        if (idToken == null) {
            showAuthError("You must be signed in to view a meal plan.")
            currentScreen = AppScreen.LOGIN
            return
        }

        lifecycleScope.launch {
            try {
                val authorizationHeader = "Bearer $idToken"

                selectedMealPlan = ApiClient.apiService.getMealPlan(
                    authorization = authorizationHeader,
                    mealPlanId = mealPlanId
                )

                currentScreen = AppScreen.MEAL_PLAN_DETAIL

            } catch (e: Exception) {
                Log.e("Api", "Could not load meal plan", e)
                showAuthError("Could not load the meal plan.")
            }
        }
    }

    /**
     * This deletes a meal from the API
     */
    private fun deleteMealPlan(mealPlanId: Long) {
        val idToken = googleIdToken

        if (idToken == null) {
            showAuthError("You must be signed in to delete a meal plan.")
            currentScreen = AppScreen.LOGIN
            return
        }

        lifecycleScope.launch {
            try {
                val authorizationHeader = "Bearer $idToken"

                val response = ApiClient.apiService.deleteMealPlan(
                    authorization = authorizationHeader,
                    mealPlanId = mealPlanId
                )

                if (!response.isSuccessful) {
                    throw retrofit2.HttpException(response)
                }

                val updatedMealPlanResponse =
                    ApiClient.apiService.getMealPlans(authorizationHeader)

                mealPlans = updatedMealPlanResponse.content
                selectedMealPlan = null
                currentScreen = AppScreen.HOME

            } catch (e: Exception) {
                Log.e("Api", "Could not delete meal plan", e)
                showAuthError("Could not delete the meal plan.")
            }
        }
    }

    /**
     * This updates the meal plan in the API
     */
    private fun updateMealPlan(
        mealPlanId: Long,
        weekStart: String
    ) {
        val idToken = googleIdToken

        if (idToken == null) {
            showAuthError("You must be signed in to update a meal plan.")
            currentScreen = AppScreen.LOGIN
            return
        }

        lifecycleScope.launch {
            try {
                val authorizationHeader = "Bearer $idToken"

                selectedMealPlan = ApiClient.apiService.updateMealPlan(
                    authorization = authorizationHeader,
                    mealPlanId = mealPlanId,
                    request = MealPlanPatch(weekStart)
                )

                val updatedMealPlanResponse =
                    ApiClient.apiService.getMealPlans(authorizationHeader)

                mealPlans = updatedMealPlanResponse.content
                currentScreen = AppScreen.MEAL_PLAN_DETAIL

            } catch (e: Exception) {
                Log.e("Api", "Could not update meal plan", e)
                showAuthError("Could not update the meal plan.")
            }
        }
    }
    /**
     * This will log the user out of the app
     */
    private fun logout() {
        googleIdToken = null
        userDisplayName = ""
        mealPlans = emptyList()
        selectedMealPlan = null
        authError = null
        currentScreen = AppScreen.LOGIN
    }
    /**
     * This delete accounts
     */
    private fun deleteAccount() {
        val idToken = googleIdToken

        if (idToken == null) {
            showAuthError("You must be signed in to delete your account.")
            currentScreen = AppScreen.LOGIN
            return
        }

        lifecycleScope.launch {
            try {
                val authorizationHeader = "Bearer $idToken"

                val response = ApiClient.apiService.deleteCurrentUser(
                    authorization = authorizationHeader
                )

                if (!response.isSuccessful) {
                    throw retrofit2.HttpException(response)
                }

                logout()

            } catch (e: Exception) {
                Log.e("Api", "Could not delete account", e)
                showAuthError("Could not delete your account.")
            }
        }
    }
    /**
     * This function will load all of our users
     */
    private fun loadUsers() {
        val idToken = googleIdToken

        if (idToken == null) {
            showAuthError("You must be signed in to view users.")
            currentScreen = AppScreen.LOGIN
            return
        }

        lifecycleScope.launch {
            try {
                val authorizationHeader = "Bearer $idToken"

                val response = ApiClient.apiService.getUsers(
                    authorization = authorizationHeader
                )

                adminUsers = response.content
                currentScreen = AppScreen.ADMIN

            } catch (e: Exception) {
                Log.e("Api", "Could not load users", e)
                showAuthError("Could not load the user list.")
            }
        }
    }

    private fun changeUserRole(user: UserResponse) {
        val idToken = googleIdToken

        if (idToken == null) {
            showAuthError("You must be signed in to change user roles.")
            currentScreen = AppScreen.LOGIN
            return
        }

        lifecycleScope.launch {
            try {
                val authorizationHeader = "Bearer $idToken"

                val newRole = if (user.role == "ADMIN") {
                    "USER"
                } else {
                    "ADMIN"
                }

                ApiClient.apiService.updateUserRole(
                    authorization = authorizationHeader,
                    userId = user.id,
                    request = UserRoleUpdate(newRole)
                )

                loadUsers()

            } catch (e: Exception) {
                Log.e("Api", "Could not change user role", e)
                showAuthError("Could not change the user role.")
            }
        }
    }

    private fun deleteUser(user: UserResponse) {
        val idToken = googleIdToken

        if (idToken == null) {
            showAuthError("You must be signed in to delete users.")
            currentScreen = AppScreen.LOGIN
            return
        }

        lifecycleScope.launch {
            try {
                val authorizationHeader = "Bearer $idToken"

                val response = ApiClient.apiService.deleteUser(
                    authorization = authorizationHeader,
                    userId = user.id
                )

                if (!response.isSuccessful) {
                    throw retrofit2.HttpException(response)
                }

                loadUsers()

            } catch (e: Exception) {
                Log.e("Api", "Could not delete user", e)
                showAuthError("Could not delete the user.")
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
                    googleIdToken = idTokenResult
                    loadCurrentUser(idTokenResult)
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
