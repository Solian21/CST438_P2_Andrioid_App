package com.example.cst438_p2_android_app.ui.home

import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.*


@Composable
fun DialMenuNav(
    modifier: Modifier = Modifier,
    onAddRecipeClick: () -> Unit = {},
    onUpdateMealPlanClick: () -> Unit = {}
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (isExpanded) {
            SmallFloatingActionButton(
                onClick = {
                    isExpanded = false
                    onAddRecipeClick()
                }
            ) {
                Text("Recipe")
            }

            SmallFloatingActionButton(
                onClick = {
                    isExpanded = false
                    onUpdateMealPlanClick()
                }
            ) {
                Text("Plan")
            }
        }

        FloatingActionButton(
            onClick = {
                isExpanded = !isExpanded
            }
        ) {
            Text(
                text = if (isExpanded) "×" else "+"
            )
        }
    }
}