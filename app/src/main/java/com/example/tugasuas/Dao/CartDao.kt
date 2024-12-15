package com.example.tugasuas.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.tugasuas.model.Cart

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insert(cart: Cart)

    @Update
    fun update(cart: Cart)

    @Delete
    fun delete(cart: Cart)
}