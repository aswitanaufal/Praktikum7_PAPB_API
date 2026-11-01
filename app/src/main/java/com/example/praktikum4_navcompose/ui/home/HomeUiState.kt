package com.example.praktikum4_navcompose.ui.home

import com.example.praktikum4_navcompose.data.model.Student

data class HomeUiState(
    val students: List<Student> = emptyList(),
    val searchQuery: String ="",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
