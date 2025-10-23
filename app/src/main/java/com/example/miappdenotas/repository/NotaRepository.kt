package com.example.miappdenotas.repository

import androidx.lifecycle.LiveData
import com.example.miappdenotas.model.Nota
import com.example.miappdenotas.model.NotaDao

class NotaRepository(private val notaDao: NotaDao) {

    // Ya no se usa directamente, pero se mantiene para consistencia.
    val todasLasNotas: LiveData<List<Nota>> = notaDao.obtenerNotasPorFechaDesc()

    suspend fun insertar(nota: Nota) {
        notaDao.insertar(nota)
    }

    suspend fun insertarLista(notas: List<Nota>) {
        notaDao.insertarLista(notas)
    }

    suspend fun actualizar(nota: Nota) {
        notaDao.actualizar(nota)
    }

    suspend fun eliminar(nota: Nota) {
        notaDao.eliminar(nota)
    }

    suspend fun obtenerTodasLasNotasList(): List<Nota> {
        return notaDao.obtenerTodasLasNotasList()
    }

    suspend fun reemplazarTodasLasNotas(notas: List<Nota>) {
        notaDao.reemplazarTodasLasNotas(notas)
    }

    // 🛑 FUNCIÓN DE BÚSQUEDA
    fun buscarNotas(searchQuery: String): LiveData<List<Nota>> {
        return notaDao.buscarNotas(searchQuery)
    }

    // 🛑 FUNCIONES DE ORDENACIÓN
    fun obtenerNotasPorFechaDesc(): LiveData<List<Nota>> {
        return notaDao.obtenerNotasPorFechaDesc()
    }

    fun obtenerNotasPorFechaAsc(): LiveData<List<Nota>> {
        return notaDao.obtenerNotasPorFechaAsc()
    }
}