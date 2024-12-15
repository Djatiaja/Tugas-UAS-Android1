package com.example.tugasuas.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

data class User(

    val _id:String?=null,

    val nama:String,
    val email:String,
    val password:String,
)
