package com.example.tugasuas.sharePref

import android.content.Context
import android.content.SharedPreferences
import com.example.tugasuas.model.Furniture
import com.example.tugasuas.model.User
import com.google.gson.Gson

class PrefManager private constructor(context: Context){

    private val sharedPreferences:SharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    companion object{
        private const val FILE_NAME ="SharedPreferencesApp"
        @Volatile
        private var instance:PrefManager?=null
        fun getInstance(context: Context):PrefManager{
            return instance?: synchronized(this){
                instance?:PrefManager(context.applicationContext).also {
                    instance= it
                }
            }
        }

        private val KEY_USERNAME = "username"
    }
    fun saveUsername(username:String){
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USERNAME, username)
        editor.apply()
    }

    fun getUsername(): String {
        return sharedPreferences.getString(KEY_USERNAME, "").orEmpty()
    }

    fun clear(){
        sharedPreferences.edit().also {
            it.clear()
            it.apply()
        }
    }

    fun saveUser(user: User){
        val editor = sharedPreferences.edit()
        editor.putString("User", Gson().toJson(user))
        editor.apply()
    }

    fun getUser(): User? {
        val data = sharedPreferences.getString("User", "").orEmpty()
        return Gson().fromJson(data, User::class.java)
    }


    fun saveData(furnitures : List<Furniture>){
        val editor = sharedPreferences.edit()
        editor.putString("Funitures1", Gson().toJson(furnitures))
        editor.apply()
    }

    fun getData(): List<Furniture> {
        val data = sharedPreferences.getString("Funitures1", null) // Use null as the default value
        return if (data.isNullOrEmpty()) {
            emptyList() // Return an empty list if data is null or empty
        } else {
            Gson().fromJson(data, Array<Furniture>::class.java).toList()
        }
    }

}