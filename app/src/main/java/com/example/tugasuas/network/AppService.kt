package com.example.tugasuas.network

import com.example.tugasuas.model.Furniture
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AppService {
    @GET("furnitures")
    fun getAllFurnitures():Call<List<Furniture>>

    @GET("/furnitures/{id}")
    fun getFurnitures(@Path("id") id:String):Call<List<Furniture>>
}