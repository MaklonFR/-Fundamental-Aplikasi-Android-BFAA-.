package com.maklon.fr.githubuserapp

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.maklon.fr.githubuserapp.databinding.ActivityUserDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetails : AppCompatActivity() {

   companion object {
       const val  Extra_USERNAME = "extra_username"
   }

    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var detailUserViewModel : DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "User Profile"

        val username = intent.getStringExtra(Extra_USERNAME)
        detailUserViewModel = ViewModelProvider (this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)

        detailUserViewModel.setDetailUsers(username.toString())
        detailUserViewModel.getDetailUsers().observe(this, {
            if (it !=null) {
                binding.apply {
                    detailName.text = it.name
                    detailUsername.text = it.login
                    detailLocation.text = it.location
                    detailRepo.text = it.publicRepos.toString()
                    detailFollowers.text = it.followers.toString()
                    detailFollowing.text = it.following.toString()
                    Glide.with(this@UserDetails)
                        .load(it.avatarUrl)
                        .circleCrop()
                        .into(imgDetailPhoto)
                }
            }
        })

        val fragmetPagerAdapter = FragmentPagerAdapter (this, supportFragmentManager)
        binding.apply {
            viewPager.adapter = fragmetPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
 }

}