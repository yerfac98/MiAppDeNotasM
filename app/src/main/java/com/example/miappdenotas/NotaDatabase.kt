package com.example.miappdenotas

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.miappdenotas.model.Nota
import com.example.miappdenotas.model.NotaDao

// @Database indica que esta es la clase de la base de datos
// Ya no necesitamos @TypeConverters, ya que Nota.fecha es un Long
@Database(entities = [Nota::class], version = 1, exportSchema = false)
abstract class NotaDatabase : RoomDatabase() {

    // Función abstracta para obtener el DAO
    abstract fun obtenerNotaDao(): NotaDao

    // Companion Object para implementar el patrón Singleton
    companion object {
        @Volatile
        private var INSTANCIA: NotaDatabase? = null

        fun obtenerInstancia(context: Context): NotaDatabase {
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    NotaDatabase::class.java,
                    "nota_db" // Nombre del archivo de la base de datos
                )
                    // CORRECCIÓN: Se elimina .allowMainThreadQueries()
                    // Las operaciones de Room ahora DEBEN ejecutarse con 'suspend'
                    // o dentro de un 'CoroutineScope' para evitar bloqueos de UI.
                    .build() // Crea e inicializa la base de datos

                INSTANCIA = instancia
                instancia
            }
        }
    }
}