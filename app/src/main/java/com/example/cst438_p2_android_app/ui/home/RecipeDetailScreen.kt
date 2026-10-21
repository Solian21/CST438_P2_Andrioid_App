package com.example.cst438_p2_android_app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cst438_p2_android_app.network.RecipeResponse
import com.example.cst438_p2_android_app.ui.theme.AppDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipe: RecipeResponse,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text("Back")
                    }
                },
                title = {
                    Text("Recipe Details")
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
                .padding(AppDimensions.screenPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(
                AppDimensions.sectionSpacing
            )
        ) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.titleLarge
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(
                        AppDimensions.cardPadding
                    ),
                    verticalArrangement = Arrangement.spacedBy(
                        AppDimensions.itemSpacing
                    )
                ) {
                    if (recipe.ingredients.isEmpty()) {
                        Text("No ingredients listed.")
                    } else {
                        recipe.ingredients.forEach { ingredient ->
                            Text("• ${ingredient.ingredientName}")
                        }
                    }
                }
            }

            Text(
                text = "Measurements",
                style = MaterialTheme.typography.titleLarge
            )

            Text(text = recipe.measurements ?: "No measurements provided.")

            Text(
                text = "Instructions",
                style = MaterialTheme.typography.titleLarge
            )

            Text(text = recipe.instructions ?: "No instructions provided.")

            Spacer(
                modifier = Modifier.height(AppDimensions.itemSpacing)
            )

            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Recipes")
            }
        }
    }
}
