package com.maklon.fr.githubuserapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDaoFavorite {

    @Insert
    suspend fun  addFavorite (userFavorite: UserFavorite)

    @Query ("SELECT * FROM user_favorite")
    fun getUserFavorite (): LiveData<List <UserFavorite>>

    @Query ("SELECT count(*) FROM user_favorite where user_favorite.id= :id")
    suspend fun checkUser(id:Int):Int

    @Query ("DELETE FROM user_favorite WHERE user_favorite.id= :id")
    suspend fun removeFromFavorite (id:Int): Int
}