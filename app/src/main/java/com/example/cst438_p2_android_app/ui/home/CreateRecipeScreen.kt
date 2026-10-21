package com.example.cst438_p2_android_app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.cst438_p2_android_app.network.IngredientResponse
import com.example.cst438_p2_android_app.network.RecipeCreate
import com.example.cst438_p2_android_app.ui.theme.AppDimensions
import androidx.compose.material3.MaterialTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecipeScreen(
    ingredients: List<IngredientResponse>,
    onBackClick: () -> Unit,
    onCreateClick: (RecipeCreate) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var measurements by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var ingredientSearch by remember { mutableStateOf("") }

    val selectedIngredientIds = remember {
        mutableStateListOf<Long>()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text("Back")
                    }
                },
                title = {
                    Text("Create Recipe")
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
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Recipe Name")
                },
                singleLine = true
            )

            OutlinedTextField(
                value = measurements,
                onValueChange = { measurements = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Measurements")
                }
            )

            OutlinedTextField(
                value = instructions,
                onValueChange = { instructions = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Instructions")
                },
                minLines = 4
            )

            Text("Select Ingredients")

            OutlinedTextField(
                value = ingredientSearch,
                onValueChange = { ingredientSearch = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search Ingredients") },
                singleLine = true
            )

            val filteredIngredients = ingredients.filter { ingredient ->
                ingredient.ingredientName.contains(
                    ingredientSearch,
                    ignoreCase = true
                )
            }

            if (ingredients.isEmpty()) {
                Text("No ingredients found.")
            } else if (filteredIngredients.isEmpty()) {
                Text("No matching ingredients found.")
            } else {
                filteredIngredients.forEach { ingredient ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedIngredientIds.contains(
                                ingredient.id
                            ),
                            onCheckedChange = { checked ->
                                if (checked) {
                                    selectedIngredientIds.add(ingredient.id)
                                } else {
                                    selectedIngredientIds.remove(ingredient.id)
                                }
                            }
                        )

                        Text(ingredient.ingredientName)
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(AppDimensions.itemSpacing)
            )

            Button(
                onClick = {
                    onCreateClick(
                        RecipeCreate(
                            name = name.trim(),
                            ingredientIds = selectedIngredientIds.toSet(),
                            measurements = measurements.trim()
                                .ifBlank { null },
                            instructions = instructions.trim()
                                .ifBlank { null }
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank()
            ) {
                Text("Create Recipe")
            }
        }
    }
}
