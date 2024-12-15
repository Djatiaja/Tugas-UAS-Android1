package com.example.tugasuas.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tugasuas.model.Bookmark
import com.example.tugasuas.model.Cart

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insert(cart: Cart)

    @Update
    fun update(cart: Cart)

    @Delete
    fun delete(cart: Cart)

    @Query("SELECT * FROM carts WHERE `key`= :pkey  LIMIT 1")
    fun getCart(pkey:String): Cart?

    @Query("SELECT * FROM carts  WHERE isCheckout = 0 AND userID= :uid ")
    fun getALlCart(uid:String):List<Cart>

    @Query("SELECT * FROM carts  WHERE isPurchase = 1  AND userID= :uid")
    fun getALlPurchase(uid:String):List<Cart>

    @Query("SELECT * FROM carts WHERE isCheckout = 1  AND userID= :uid")
    fun getALlCartCheckout(uid:String):List<Cart>
}