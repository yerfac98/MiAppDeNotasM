package com.example.miappdenotas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.miappdenotas.model.Nota
import com.example.miappdenotas.repository.NotaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// 🛑 Enumeración para el estado de ordenamiento
enum class SortOrder {
    DATE_DESC, // Más Reciente (Default)
    DATE_ASC   // Más Antigua
}

class NotaViewModel(private val repository: NotaRepository) : ViewModel() {

    private val _searchQuery = MutableLiveData<String>()
    private val _sortOrder = MutableLiveData<SortOrder>()

    // LiveData pública que la UI observará
    val notasFiltradas = MediatorLiveData<List<Nota>>()

    private var currentSource: LiveData<List<Nota>>? = null

    init {
        // Inicializa los estados
        _searchQuery.value = ""
        _sortOrder.value = SortOrder.DATE_DESC // Default: Más reciente

        // Observa el cambio del término de búsqueda y el orden
        notasFiltradas.addSource(_searchQuery) { updateNotesSource() }
        notasFiltradas.addSource(_sortOrder) { updateNotesSource() }
    }

    private fun updateNotesSource() {
        val query = _searchQuery.value
        val order = _sortOrder.value ?: SortOrder.DATE_DESC

        val newSource = if (!query.isNullOrEmpty() && query != "%%") {
            // Caso 1: Hay una búsqueda activa. Usamos la query de búsqueda.
            repository.buscarNotas(query)
        } else {
            // Caso 2: No hay búsqueda. Aplicar el orden seleccionado.
            when (order) {
                SortOrder.DATE_DESC -> repository.obtenerNotasPorFechaDesc()
                SortOrder.DATE_ASC -> repository.obtenerNotasPorFechaAsc()
            }
        }

        if (newSource == currentSource) return

        // 1. Elimina la fuente anterior (si existe)
        currentSource?.let {
            notasFiltradas.removeSource(it)
        }

        // 2. Añade la nueva fuente
        notasFiltradas.addSource(newSource) { notes ->
            notasFiltradas.value = notes
        }

        // 3. Guarda la nueva fuente como la actual
        currentSource = newSource
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSortOrder(order: SortOrder) {
        if (_sortOrder.value != order) {
            _sortOrder.value = order
        }
    }

    // -----------------------------------------------------------------
    // OPERACIONES CRUD Y ARCHIVO
    // -----------------------------------------------------------------

    fun insertar(nota: Nota) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertar(nota)
    }

    fun insertarLista(notas: List<Nota>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertarLista(notas)
    }

    fun actualizar(nota: Nota) = viewModelScope.launch(Dispatchers.IO) {
        repository.actualizar(nota)
    }

    fun eliminar(nota: Nota) = viewModelScope.launch(Dispatchers.IO) {
        repository.eliminar(nota)
    }

    suspend fun obtenerTodasLasNotasList(): List<Nota> {
        delay(200)
        return withContext(Dispatchers.IO) {
            repository.obtenerTodasLasNotasList()
        }
    }

    fun reemplazarTodasLasNotas(notas: List<Nota>) = viewModelScope.launch(Dispatchers.IO) {
        repository.reemplazarTodasLasNotas(notas)
    }

    // -----------------------------------------------------------------
    // FACTORY ANIDADA
    // -----------------------------------------------------------------
    companion object {
        class NotaViewModelFactory(private val repository: NotaRepository) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(NotaViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return NotaViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}