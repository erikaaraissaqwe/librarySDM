package br.edu.ifsp.scl.library.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.library.data.Book
import br.edu.ifsp.scl.library.data.BookDatabase
import br.edu.ifsp.scl.library.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application): AndroidViewModel(application) {
    private val repository: BookRepository
    var allBooks : LiveData<List<Book>>
    lateinit var book : LiveData<Book>

    init {
        val dao = BookDatabase.getDatabase(application).bookDAO()
        repository = BookRepository(dao)
        allBooks = repository.getAllBooks()
    }

    fun insert(book: Book) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(book)
    }

    fun update(book: Book) = viewModelScope.launch(Dispatchers.IO){
        repository.update(book)
    }

    fun delete(book: Book) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(book)
    }


    fun getBookById(id: Int) {
        viewModelScope.launch {
           book = repository.getBookById(id)
        }


    }


}