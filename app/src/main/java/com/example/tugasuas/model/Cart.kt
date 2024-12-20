package com.example.tugasuas.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "carts")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    @NotNull
    val id:Int = 0,

    @ColumnInfo(name = "key")
    val key: String,

    @ColumnInfo(name = "quantity")
    var quantity:Int,

    @ColumnInfo(name = "isCheckout")
    var isCheckout:Boolean,

    var isPurchase:Boolean = false,

    var userID:String
)