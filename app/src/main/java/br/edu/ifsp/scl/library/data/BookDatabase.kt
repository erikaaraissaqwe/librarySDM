package br.edu.ifsp.scl.library.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Book::class], version = 1)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDAO(): BookDAO

    companion object {
        @Volatile
        private var INSTANCE: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "booklist.db"
                ).build()
                INSTANCE = instance
                // return instance
                instance

            }
        }
    }
}