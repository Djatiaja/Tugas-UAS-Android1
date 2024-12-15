package com.example.tugasbookmark.database

import android.content.Context
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.tugasuas.Dao.BookmarkDao
import com.example.tugasuas.Dao.CartDao
import com.example.tugasuas.model.Bookmark
import com.example.tugasuas.model.Cart

@androidx.room.Database (entities =[Bookmark::class, Cart::class], version=1, exportSchema=false)
abstract class database: RoomDatabase() {
    abstract fun bookmarkDao():BookmarkDao?
    abstract fun cartDao(): CartDao?
    companion object{
        @Volatile
        private var INSTANCE:database?=null
        fun getDatabase(context : Context):database?{
            if (INSTANCE==null){
                synchronized(database::class.java){
                    INSTANCE = databaseBuilder(
                        context.applicationContext,
                        database::class.java, "database"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}