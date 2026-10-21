package com.example.cst438_p2_android_app.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cst438_p2_android_app.network.RecipeResponse
import com.example.cst438_p2_android_app.ui.theme.CST438_P2_Android_APPTheme
import com.example.cst438_p2_android_app.ui.theme.AppDimensions
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier,
    recipes: List<RecipeResponse> = emptyList(),
    onBackClick: () -> Unit = {},
    onRefreshClick: () -> Unit = {},
    onRecipeClick: (Long) -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text("Back")
                    }
                },
                title = {
                    Text("Premade Recipes")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
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
            Button(
                onClick = onRefreshClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Refresh Premade Recipes")
            }

            if (recipes.isEmpty()) {
                Text("No recipes found.")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(
                        AppDimensions.itemSpacing
                    )
                ) {
                    items(
                        items = recipes,
                        key = { recipe -> recipe.id }
                    ) { recipe ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onRecipeClick(recipe.id)
                                }
                        ) {
                            Column(
                                modifier = Modifier.padding(
                                    AppDimensions.cardPadding
                                )
                            ) {
                                Text(
                                    text = recipe.name,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Text(
                                    text = "${recipe.ingredients.size} ingredients"
                                )

                                Text("Tap to view details")
                            }
                        }
                    }
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
