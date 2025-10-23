package com.example.miappdenotas.viewmodel

// NotaViewModelFactory.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miappdenotas.repository.NotaRepository
import java.lang.IllegalArgumentException

// ESTA LÍNEA ES LA QUE FALTA
import com.example.miappdenotas.viewmodel.NotaViewModel
// ----------------------------

class NotaViewModelFactory(private val repository: NotaRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotaViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}