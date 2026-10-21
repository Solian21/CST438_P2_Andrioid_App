/**
 * This is the home page that users will see after they log in
 * They should have Recipe Cards that they can view 3 random recipes
 * the next column is going to be the meal plans.
 * If possible I would want them to be some kind of a carousel where the user can view Monday-Sunday
 */
package com.example.cst438_p2_android_app.ui.home


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cst438_p2_android_app.ui.theme.CST438_P2_Android_APPTheme
import com.example.cst438_p2_android_app.ui.home.Login
import com.example.cst438_p2_android_app.ui.theme.AppDimensions
import com.example.cst438_p2_android_app.network.MealPlanResponse
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


// Needed to add this so that Kotlin knows I am aware the things are going to change
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userName: String,
    modifier: Modifier = Modifier,
    mealPlans: List<MealPlanResponse> = emptyList(),
    onAddRecipeClick: () -> Unit = {},
    onAddIngredientClick: () -> Unit = {},
    onViewRecipesClick: () -> Unit = {},
    onUpdateMealPlanClick: () -> Unit = {},
    onMealPlanClick: (Long) -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onDeleteAccountClick: () -> Unit = {},
    isAdmin: Boolean = false,
    onAdminClick: () -> Unit = {}

) {
    var showDeleteAccountDialog by remember {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("My Recipes")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        /**
         * I want to use this to have the user add their own recipes
         * I was thinking it could also expand to add meal plans almost like a navigation bar
         */

        floatingActionButton = {
            DialMenuNav(
                onAddRecipeClick = onAddRecipeClick,
                onAddIngredientClick = onAddIngredientClick,
                onViewRecipesClick = onViewRecipesClick,
                onUpdateMealPlanClick = onUpdateMealPlanClick
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(AppDimensions.screenPadding),
            verticalArrangement = Arrangement.spacedBy(
                AppDimensions.sectionSpacing
            )

        ) {
            Text(
                text = "Welcome $userName!",
            )

            Text(
                text = "Meal Plans"
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(AppDimensions.cardPadding)
                ) {
                    if (mealPlans.isEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(AppDimensions.cardPadding)
                            ) {
                                Text("No meal plans found.")

                                Spacer(
                                    modifier = Modifier.height(AppDimensions.itemSpacing)
                                )

                                Button(
                                    onClick = onUpdateMealPlanClick
                                ) {
                                    Text("Create Meal Plan")
                                }
                            }
                        }
                    } else {
                        mealPlans.forEach { mealPlan ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onMealPlanClick(mealPlan.id)
                                    }
                            ) {
                                Column(
                                    modifier = Modifier.padding(AppDimensions.cardPadding)
                                ) {
                                    Text(
                                        text = "Week of ${mealPlan.weekStart}",
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Text(
                                        text = "${mealPlan.entries.size} planned meals"
                                    )

                                    Spacer(
                                        modifier = Modifier.height(AppDimensions.itemSpacing)
                                    )

                                    Text("Tap to view details")
                                }
                            }
                        }
                    }
                }
            }
            Button(
                onClick = onLogoutClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Log Out")
            }
            OutlinedButton(
                onClick = {
                    showDeleteAccountDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Delete Account")
            }
            if (showDeleteAccountDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDeleteAccountDialog = false
                    },
                    title = {
                        Text("Delete Account?")
                    },
                    text = {
                        Text(
                            "This permanently deletes your account and its data. This action cannot be undone."
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDeleteAccountDialog = false
                                onDeleteAccountClick()
                            }
                        ) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDeleteAccountDialog = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
            if (isAdmin) {
                Button(
                    onClick = onAdminClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Admin Panel")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CST438_P2_Android_APPTheme {
        HomeScreen(userName = "Preview User")
    }
}
