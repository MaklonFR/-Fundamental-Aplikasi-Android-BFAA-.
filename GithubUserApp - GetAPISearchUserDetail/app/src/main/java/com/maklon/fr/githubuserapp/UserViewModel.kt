package com.maklon.fr.githubuserapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Query

class UserViewModel: ViewModel() {

    val lisUser = MutableLiveData<ArrayList<userItem>>()

    fun setSearchUsers (query: String) {
        UserAPIConfig.getApiService()
            .searchUser(query)
            .enqueue(object :Callback<userResponse>{
                override fun onResponse(
                    call: Call<userResponse>,
                    response: retrofit2.Response<userResponse>
                ) {
                    if (response.isSuccessful) {
                        lisUser.postValue(response.body()?.items as ArrayList<userItem>?)
                    }
                }

                override fun onFailure(call: Call<userResponse>, t: Throwable) {
                    Log.d("Failre", t.message.toString())
                }

            })
    }

    fun getSetUsers (): LiveData<ArrayList<userItem>>{
        return lisUser
    }

}