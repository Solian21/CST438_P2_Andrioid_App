package com.example.cst438_p2_android_app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cst438_p2_android_app.network.MealPlanResponse
import com.example.cst438_p2_android_app.ui.theme.AppDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealPlanDetailScreen(
    mealPlan: MealPlanResponse,
    onBackClick: () -> Unit,
    onUpdateClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text("Back")
                    }
                },
                title = { Text("Meal Plan Details") },
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
            text = "Week of ${mealPlan.weekStart}",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Planned meals: ${mealPlan.entries.size}"
        )

        mealPlan.entries.forEach { entry ->
            Text(
                text = "${entry.plannedDate} - ${entry.mealType}"
            )
        }

        Spacer(
            modifier = Modifier.height(AppDimensions.itemSpacing)
        )

        Button(
            onClick = onUpdateClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Meal Plan")
        }

        Button(
            onClick = onDeleteClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Delete Meal Plan")
        }

            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}
