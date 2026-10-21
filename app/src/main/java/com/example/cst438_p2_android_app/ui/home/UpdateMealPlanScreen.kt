package com.example.cst438_p2_android_app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.cst438_p2_android_app.ui.theme.AppDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateMealPlanScreen(
    currentWeekStart: String,
    onBackClick: () -> Unit = {},
    onUpdateClick: (String) -> Unit = {}
) {
    var weekStart by remember {
        mutableStateOf(currentWeekStart)
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
                    Text("Update Meal Plan")
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
            Text(
                text = "Change the starting date for this meal plan.",
                style = MaterialTheme.typography.bodyLarge
            )

            OutlinedTextField(
                value = weekStart,
                onValueChange = { weekStart = it },
                modifier = Modifier.padding(AppDimensions.smallPadding),
                label = {
                    Text("Week start")
                },
                placeholder = {
                    Text("YYYY-MM-DD")
                },
                singleLine = true
            )

            Button(
                onClick = {
                    onUpdateClick(weekStart)
                },
                enabled = weekStart.isNotBlank()
            ) {
                Text("Save Changes")
            }
        }
    }
}
