package br.edu.ifsp.scl.library.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface BookDAO {
    @Insert
    suspend fun insert(book: Book)

    @Update
    suspend fun update (book: Book)

    @Delete
    suspend fun delete(book: Book)

    @Query("SELECT * FROM book ORDER BY titulo")
    fun getAllBooks(): LiveData<List<Book>>

    @Query("SELECT * FROM book WHERE id=:id")
    fun getBookById(id: Int): LiveData<Book>


}