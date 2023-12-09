package br.edu.ifsp.scl.library.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var titulo: String,
    var isbn: String,
    var img: String,
)