package com.example.praktikum4_navcompose.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.praktikum4_navcompose.data.repository.StudentRepository

// yg dipanggil adalah interfacenya, agar
// di private agar dibatasi agar bisa ambil data di model sj
// objek repo ini apa? pakai ":" yg berarti repo = StudentRepository
class DetailViewModel (private val repository: StudentRepository): ViewModel() {
    var uiState by mutableStateOf(DetailUiState())
        private set

    fun loadStudent(id: Int) {
        uiState = uiState.copy(isLoading = true, errorMessage = null)
        repository.getStudentById(id) { result ->
            result
                .onSuccess { student ->
                    uiState = DetailUiState(student = student)
                }
                .onFailure { error ->
                    uiState = DetailUiState(
                        errorMessage = error.message ?: "Data tidak tersedia"
                    )
                }
        }
    }

//        private set fun loadStudent(id: Int){
//            val student = repository.getStudentById(id)
//        uiState = if (student != null){
//            DetailUiState(student = student)
//        }else{
//            DetailUiState(errorMsg = "Data Tidak Ditemukan")
//        }
//        }

    // variabel statis: harus ada isi diawal
    // variabel val: boleh null di awal
    // karena ViewModel implement fun tertentu (jadi harus override fun nya jg)
    companion object{
        // factory u/ modif komponen lainnya
        fun factory(repository: StudentRepository): ViewModelProvider.Factory=object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            // panggil fun dari ViewModel
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailViewModel(repository) as T
            }
        }
    }
}
