package com.example.praktikum4_navcompose.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.praktikum4_navcompose.data.repository.StudentRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: StudentRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var hasUserInteracted = false

    init {
       observeSearch()
    }

    fun loadStudents() {
       refresh()
    }

    fun refresh() {
       viewModelScope.launch {
           val query = _searchQuery.value.trim()
           _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

           try {
               val data = if (query.isEmpty()) {
                   repository.getStudents()
               } else {
                   repository.searchStudents(query)
               }

               _uiState.value = _uiState.value.copy(
                   students = data,
                   searchQuery = query,
                   isLoading = false,
                   errorMessage = null
               )
           } catch (e: CancellationException) {
               throw e
           } catch (e: Exception) {
               _uiState.value = _uiState.value.copy(
                   isLoading = false,
                   errorMessage = e.message ?: "Gagal mengambil data"
               )
           }
       }
    }

    fun onSearchQueryChange(query: String) {
       hasUserInteracted = true
       _searchQuery.value = query
       _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
       viewModelScope.launch {
           searchQuery
               .debounce(300)
               .distinctUntilChanged()
               .collectLatest { query ->
                   if (!hasUserInteracted) return@collectLatest
                   refresh()
               }
       }
    }

    companion object {
        fun factory(repository: StudentRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(repository) as T
                }
            }
    }
}