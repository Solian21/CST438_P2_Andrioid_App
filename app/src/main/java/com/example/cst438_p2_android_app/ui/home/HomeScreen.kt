package com.example.cst438_p2_android_app.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.cst438_p2_android_app.ui.theme.CST438_P2_Android_APPTheme

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
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddRecipeClick
            ) {
                Text("+")
            }
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
                text = "Welcome!",
            )

            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Search recipes")
                },
                placeholder = {
                    Text("Search coming soon")
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