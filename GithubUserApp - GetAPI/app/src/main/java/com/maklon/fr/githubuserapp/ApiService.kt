package com.maklon.fr.githubuserapp

import retrofit2.Call
import retrofit2.http.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiService {
    @GET("/users")
    fun getUser() : Call<List<UsersResponse>>

    companion object {

        var BASE_URL = "https://api.github.com/"

        fun create() : ApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)

        }
    }
}

