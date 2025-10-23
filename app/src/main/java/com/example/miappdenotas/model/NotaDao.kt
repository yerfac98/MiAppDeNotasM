package com.example.miappdenotas.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface NotaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(nota: Nota)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarLista(notas: List<Nota>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun actualizar(nota: Nota)

    @Delete
    suspend fun eliminar(nota: Nota)

    // 🛑 ORDENACIÓN DESCENDENTE (Más Reciente)
    @Query("SELECT * FROM notas_table ORDER BY id DESC")
    fun obtenerNotasPorFechaDesc(): LiveData<List<Nota>>

    // 🛑 ORDENACIÓN ASCENDENTE (Más Antigua)
    @Query("SELECT * FROM notas_table ORDER BY id ASC")
    fun obtenerNotasPorFechaAsc(): LiveData<List<Nota>>

    // 🛑 FUNCIÓN DE BÚSQUEDA
    @Query("SELECT * FROM notas_table WHERE titulo LIKE :searchQuery OR contenido LIKE :searchQuery ORDER BY id DESC")
    fun buscarNotas(searchQuery: String): LiveData<List<Nota>>

    @Query("SELECT * FROM notas_table ORDER BY id DESC")
    suspend fun obtenerTodasLasNotasList(): List<Nota>

    @Query("DELETE FROM notas_table")
    suspend fun eliminarTodasLasNotas()

    @Transaction
    suspend fun reemplazarTodasLasNotas(notas: List<Nota>) {
        eliminarTodasLasNotas()
        insertarLista(notas)
    }
}