package com.maklon.fr.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

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

        val data = intent.getParcelableExtra<Users>("DATA")

        detailName.text= data?.data_name.toString()
        detailUsername.text =data?.data_username.toString()
        detailLocation.text = data?.data_location.toString()

        Glide.with(this)
            .load(data?.data_avatar.toString()) // URL Gambar
            .circleCrop() // Mengubah image menjadi lingkaran
            .into(detailPhoto) // imageView mana yang akan diterapkan

        //detailPhoto.setImageResource(data?.data_avatar.hashCode())
        detailCompany.text = data?.data_company.toString()
        detailRepo.text = data?.data_repo.toString()
        detailFollowers.text = data?.data_follower.toString()
        detailFollowing.text = data?.data_following.toString()

    }


}