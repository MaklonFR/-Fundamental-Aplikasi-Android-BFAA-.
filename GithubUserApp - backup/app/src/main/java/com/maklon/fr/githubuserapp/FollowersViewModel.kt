package com.maklon.fr.githubuserapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback

class FollowersViewModel : ViewModel() {

    val listFollowers = MutableLiveData<ArrayList<UserItem>>()

    fun setListFollowers(username: String) {
        UserAPIConfig.getApiService()
            .userFollowers(username)
            .enqueue(object : Callback<ArrayList<UserItem>> {
                override fun onResponse(
                    call: Call<ArrayList<UserItem>>,
                    response: retrofit2.Response<ArrayList<UserItem>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserItem>>, t: Throwable) {
                    Log.d("Failure Mistake:", t.message.toString())
                }
            })
    }

    fun getFollowersUsers(): LiveData<ArrayList<UserItem>> {
        return listFollowers
    }

}