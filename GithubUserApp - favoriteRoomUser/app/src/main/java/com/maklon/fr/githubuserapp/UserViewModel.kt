package com.maklon.fr.githubuserapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback

class UserViewModel : ViewModel() {

    val lisUser = MutableLiveData<ArrayList<UserItem>>()
    fun setSearchUsers(query: String) {
        UserAPIConfig.getApiService()
            .searchUser(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: retrofit2.Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        lisUser.postValue(response.body()?.items).toString().toLowerCase().contains(query.toLowerCase())
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getSetUsers(): LiveData<ArrayList<UserItem>> {
        return lisUser
    }

}