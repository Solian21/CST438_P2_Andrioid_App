package com.example.cst438_p2_android_app.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.cst438_p2_android_app.ui.theme.CST438_P2_Android_APPTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("Recipes")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            DialMenuNav(
                onAddRecipeClick = {
                    // Add recipe later
                },
                onUpdateMealPlanClick = {
                    // Add meal plan later
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

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Recipe Name",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Owned by: {{USERNAME}}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "Dinner recipe",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.titleLarge
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    //Add in the ingredients
                }
            }

            Text(
                text = "Instructions",
                style = MaterialTheme.typography.titleLarge
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Add in the Instructions
                }
            }

            Text(
                text = "Meal Plan",
                style = MaterialTheme.typography.titleLarge
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Planned for: Day")

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            // Connect to meal plan later
                        }
                    ) {
                        Text("Add to Meal Plan")
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        // Edit recipe later
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Edit Recipe")
                }

                Button(
                    onClick = {
                        // Delete recipe later
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Delete Recipe")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeScreenPreview() {
    CST438_P2_Android_APPTheme {
        RecipeScreen()
    }
}