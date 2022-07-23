package com.maklon.fr.githubuserapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback

class FollowingViewModel : ViewModel() {

    val listFollowing = MutableLiveData<ArrayList<UserItem>>()

    fun setListFollowing(username: String) {
        UserAPIConfig.getApiService()
            .userFollowing(username)
            .enqueue(object : Callback<ArrayList<UserItem>> {
                override fun onResponse(
                    call: Call<ArrayList<UserItem>>,
                    response: retrofit2.Response<ArrayList<UserItem>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserItem>>, t: Throwable) {
                    Log.d("Failure Mistake:", t.message.toString())
                }
            })
    }

    fun getFollowingUsers(): LiveData<ArrayList<UserItem>> {
        return listFollowing
    }

}