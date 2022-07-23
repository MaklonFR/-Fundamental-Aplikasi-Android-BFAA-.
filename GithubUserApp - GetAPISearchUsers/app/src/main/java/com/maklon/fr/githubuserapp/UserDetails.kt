package com.maklon.fr.githubuserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        supportActionBar?.title = "User Profile"

        val detailPhoto: ImageView = findViewById(R.id.img_detail_photo)
        val detailName: TextView =findViewById(R.id.detailName)
        val detailUsername: TextView =findViewById(R.id.detailUsername)
        val detailLocation: TextView =findViewById(R.id.detailLocation)
        val detailRepo: TextView =findViewById(R.id.detail_repo)
        val detailCompany: TextView =findViewById(R.id.detailCompany)
        val detailFollowers: TextView = findViewById(R.id.detail_followers)
        val detailFollowing: TextView = findViewById(R.id.detail_following)

       /* val data = intent.getParcelableExtra<usersResponse>("DATA")

        detailName.text= data?.id.toString()
        detailUsername.text =data?.login.toString()
        Glide.with(this)
            .load(data?.avatar.toString()) // URL Gambar
            .circleCrop() // Mengubah image menjadi lingkaran
            .into(detailPhoto) // imageView mana yang akan diterapkan

    val apiInterface = ApiService.create().getDetailUser(data?.login.toString())
    apiInterface.enqueue( object : Callback<ResponseDetailUser> {
        override fun onResponse(
            call: Call<ResponseDetailUser>,
            response: Response<ResponseDetailUser>
        ) {
            if (response?.body() != null) {
                detailLocation.text=response.body()!!.location
                detailRepo.text=response.body()!!.publicRepos.toString()
                detailCompany.text=response.body()!!.company
                detailFollowers.text=response.body()!!.followers.toString()
                detailFollowing.text=response.body()!!.following.toString()
            }
        }

        override fun onFailure(call: Call<ResponseDetailUser>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })*/

 }

}