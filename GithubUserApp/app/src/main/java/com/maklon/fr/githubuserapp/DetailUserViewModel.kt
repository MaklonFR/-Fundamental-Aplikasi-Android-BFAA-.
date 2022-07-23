package com.maklon.fr.githubuserapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Database
import com.maklon.fr.githubuserapp.data.local.UserDaoFavorite
import com.maklon.fr.githubuserapp.data.local.UserDatabase
import com.maklon.fr.githubuserapp.data.local.UserFavorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<DetailUserResponse>()

    private var userDao: UserDaoFavorite?
    private var userDb : UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.userDaoFavorite()
    }

    fun setDetailUsers(username: String) {
        UserAPIConfig.getApiService()
            .userDetails(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getDetailUsers(): LiveData<DetailUserResponse> {
        return user
    }

    fun addFavorite  (username: String, id: Int, avatarUrl:String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = UserFavorite (
                username,
                id,
                avatarUrl
            )
            userDao?.addFavorite(user)
        }
    }

    suspend fun checkUser (id:Int) = userDao?.checkUser(id)

    fun removeFavorite (id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }

}