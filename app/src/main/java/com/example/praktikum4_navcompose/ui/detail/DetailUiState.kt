package com.example.praktikum4_navcompose.ui.detail

import com.example.praktikum4_navcompose.data.model.Student

data class DetailUiState(
    val student: Student? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)