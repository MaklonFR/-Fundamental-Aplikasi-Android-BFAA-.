package com.maklon.fr.githubuserapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserAPI {
    @GET("search/users")
    @Headers("Authorization: token {paste your token github here...}")
    fun searchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token {paste your token github here...}")
    fun userDetails(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token {paste your token github here...}")
    fun userFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UserItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token {paste your token github here...}")
    fun userFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserItem>>
}
