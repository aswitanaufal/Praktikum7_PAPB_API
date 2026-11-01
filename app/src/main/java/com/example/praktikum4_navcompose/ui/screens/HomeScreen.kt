package com.example.praktikum4_navcompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.praktikum4_navcompose.data.model.Student
import com.example.praktikum4_navcompose.ui.home.HomeUiState

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onSearchQueryChange: (String) -> Unit,
    onSearch: ()-> Unit,
    onStudentClick: (Int) -> Unit,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Home", style = MaterialTheme.typography.headlineMedium)
        when {
            uiState.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            uiState.errorMessage != null -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(uiState.errorMessage)
                    Button(onClick = onRetry) { Text("Coba Lagi") }
                }
            }

            else -> {
                LazyColumn {
                    items(uiState.students) { student ->
                        TextButton(onClick = { onStudentClick(student.id) }) {
                            Text("${student.id} - ${student.name}")
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            TextField(
                value = uiState.searchQuery,
                onValueChange = onSearchQueryChange,
                label = { Text("Masukkan nama mahasiswa") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedButton(
                onClick = onSearch,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cari Mahasiswa")
            }
        }
    }
}