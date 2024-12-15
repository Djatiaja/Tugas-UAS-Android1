package com.example.tugasuas.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey(autoGenerate = true)
    @NotNull
    val id:Int=0,

    @ColumnInfo(name = "keyVal")
    @NotNull
    val key:String,

    val userID:String
)
