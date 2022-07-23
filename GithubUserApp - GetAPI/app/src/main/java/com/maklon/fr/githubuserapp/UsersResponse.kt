package com.maklon.fr.githubuserapp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsersResponse(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatar: String,

	@field:SerializedName("id")
	val id: Int

): Parcelable
