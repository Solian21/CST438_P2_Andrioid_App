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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.cst438_p2_android_app.ui.theme.CST438_P2_Android_APPTheme
import com.example.cst438_p2_android_app.ui.home.Login

// Needed to add this so that Kotlin knows I am aware the things are going to change
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onAddRecipeClick: () -> Unit = {}
) {
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
                DialMenuNav (
                    onAddRecipeClick = {
                        // I plan to connect this later
                    },
                    onUpdateMealPlanClick = {
                        //I also plan to connect this later
                    }

                )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Welcome! {Name}",
            )

            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Search recipes")
                },
                placeholder = {
                    Text("Discover new recipes")
                },
                readOnly = true
            )

            Text(
                text = "Recent Recipes"
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Recipe cards will go here")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Recipes will be connected later.")
                }
            }

            Text(
                text = "Meal Plans"
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Meal planning section")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {}
                    ) {
                        Text("View Meal Plans")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CST438_P2_Android_APPTheme {
        HomeScreen()
    }
}