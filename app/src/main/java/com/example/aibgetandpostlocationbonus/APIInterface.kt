package com.example.aibgetandpostlocationbonus

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIInterface {


    @GET("/test/")
    fun getData(): Call<List<MyDataItem>>


    @Headers("Content-Type: application/json")
    @POST("/test/")
    fun addUser(@Body userData: MyDataItem): Call<MyDataItem>
}