package com.example.tugasuas.model

import androidx.room.Entity


data class Furniture(
        val _id: String,
        val name: String,
        val price: Int,
        val image: String,
        val description: String,
        val category: String,
        val quantity: Int,
)
