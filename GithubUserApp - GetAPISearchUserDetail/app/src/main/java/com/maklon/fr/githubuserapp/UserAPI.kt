package com.maklon.fr.githubuserapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserAPI {
    @GET("search/users")
    @Headers("Authorization: token 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")

    fun searchUser (
        @Query ("q") query: String
    ):Call<userResponse>

    @GET ("users/{username}")
    @Headers("Authorization: token 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")
    fun userDetails (
        @Path ("username") username:String
    ): Call<DetailUserResponse>

    @GET ("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun userFollowers (
        @Path ("username") username:String
    ): Call<userResponse>

    @GET ("users/{username}/following")
    @Headers("Authorization: token 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")
    fun userFollowing (
        @Path ("username") username:String
    ): Call<userResponse>
}