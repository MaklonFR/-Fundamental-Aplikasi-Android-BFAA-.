package com.maklon.fr.githubuserapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users (
    var data_username : String,
    var data_name: String,
    var data_avatar : String,
    var data_location:String,
    var data_repo: String,
    var data_company: String,
    var data_follower: String,
    var data_following: String
) : Parcelable