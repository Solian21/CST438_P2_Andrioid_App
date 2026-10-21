package com.example.cst438_p2_android_app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cst438_p2_android_app.network.UserResponse
import com.example.cst438_p2_android_app.ui.theme.AppDimensions

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AdminScreen(
    users: List<UserResponse>,
    onBackClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onChangeRoleClick: (UserResponse) -> Unit,
    onDeleteUserClick: (UserResponse) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text("Back")
                    }
                },
                title = { Text("Admin User Panel") },
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
            Text("Refresh Users")
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(
                AppDimensions.itemSpacing
            )
        ) {
            items(
                items = users,
                key = { user -> user.id }
            ) { user ->
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
                        Text(
                            text = user.displayName ?: "Unnamed User",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(text = user.email)
                        Text(text = "User ID: ${user.id}")
                        Text(text = "Role: ${user.role}")

                        Button(
                            onClick = {
                                onChangeRoleClick(user)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                if (user.role == "ADMIN") {
                                    "Change to User"
                                } else {
                                    "Make Admin"
                                }
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                onDeleteUserClick(user)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Delete User")
                        }
                    }
                }
            }
        }

            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}
