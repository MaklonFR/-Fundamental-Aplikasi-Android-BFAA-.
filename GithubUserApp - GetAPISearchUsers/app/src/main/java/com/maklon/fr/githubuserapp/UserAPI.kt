package com.maklon.fr.githubuserapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UserAPI {
    @GET("search/users")
    @Headers("Authorization: token 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")

    fun  searchUser (
        @Query ("q") query: String
    ):Call<userResponse>

}