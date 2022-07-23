package com.maklon.fr.githubuserapp.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.maklon.fr.githubuserapp.data.local.UserDaoFavorite
import com.maklon.fr.githubuserapp.data.local.UserDatabase
import com.maklon.fr.githubuserapp.data.local.UserFavorite

class FavoriteViewModel (application: Application): AndroidViewModel(application) {

    private var userDao: UserDaoFavorite?
    private var userDb : UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.userDaoFavorite()
    }

    fun getFavoriteUser (): LiveData<List<UserFavorite>>? {
        return userDao?.getUserFavorite()
    }
}