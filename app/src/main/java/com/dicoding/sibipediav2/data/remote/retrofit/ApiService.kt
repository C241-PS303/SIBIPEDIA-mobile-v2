package com.dicoding.sibipediav2.data.remote.retrofit

import com.dicoding.sibipediav2.data.remote.request.RegisterRequest
import com.dicoding.sibipediav2.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest) : Response<RegisterResponse>

}