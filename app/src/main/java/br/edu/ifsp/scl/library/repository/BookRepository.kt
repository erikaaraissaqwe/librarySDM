package br.edu.ifsp.scl.library.repository

import androidx.lifecycle.LiveData
import br.edu.ifsp.scl.library.data.Book
import br.edu.ifsp.scl.library.data.BookDAO

class BookRepository (private val bookDAO: BookDAO) {

    suspend fun insert(book: Book){
        bookDAO.insert(book)
    }

    suspend fun update(book: Book){
        bookDAO.update(book)
    }

    suspend fun delete(book: Book){
        bookDAO.delete(book)
    }

    fun getAllBooks(): LiveData<List<Book>> {
        return bookDAO.getAllBooks()
    }

    fun getBookById(id: Int): LiveData<Book>{
        return bookDAO.getBookById(id)
    }

}