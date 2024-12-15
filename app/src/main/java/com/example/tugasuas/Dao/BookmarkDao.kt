package com.example.tugasuas.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tugasuas.model.Bookmark

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insert(Bookmark:Bookmark)

    @Update
    fun update(Bookmark:Bookmark)

    @Delete
    fun delete(Bookmark:Bookmark)

    @Query("SELECT * FROM bookmarks WHERE keyVal= :pkey AND userID= :uid LIMIT 1")
    fun getBookmark(pkey:String, uid:String): Bookmark?

    @Query("DELETE FROM bookmarks")
    fun nukeTable();

}