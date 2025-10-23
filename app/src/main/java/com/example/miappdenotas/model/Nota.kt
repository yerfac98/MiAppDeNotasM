package com.example.miappdenotas.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity le dice a Room que esta es una tabla de la base de datos
@Entity(tableName = "notas_table")
data class Nota(
    // @PrimaryKey indica que id es la clave primaria
    // autoGenerate = true le dice a Room que genere un ID automáticamente
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val titulo: String,
    val contenido: String,
    val fecha: Long = System.currentTimeMillis()
)