package com.maklon.fr.githubuserapp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    val items: ArrayList<UserItem>
) : Parcelable

@Parcelize
data class UserItem(

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("url")
    val url: String

) : Parcelable
