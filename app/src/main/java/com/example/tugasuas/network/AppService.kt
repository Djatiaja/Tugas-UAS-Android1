package com.example.tugasuas.network

import com.example.tugasuas.model.Furniture
import com.example.tugasuas.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppService {
    @GET("furnitures")
    fun getAllFurnitures():Call<List<Furniture>>

    @GET("/furnitures/{id}")
    fun getFurnitures(@Path("id") id:String):Call<List<Furniture>>

    @POST("user")
    fun postUser(@Body user: User):Call<ApiResponse>

    @GET("user")
    fun getAllUser():Call<List<User>>
}