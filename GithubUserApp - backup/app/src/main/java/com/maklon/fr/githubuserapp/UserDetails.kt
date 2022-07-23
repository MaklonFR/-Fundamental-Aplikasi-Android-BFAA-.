package com.maklon.fr.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.maklon.fr.githubuserapp.databinding.ActivityUserDetailsBinding

class UserDetails : AppCompatActivity() {

    companion object {
        const val Extra_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var detailUserViewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "User Profile"

        val username = intent.getStringExtra(Extra_USERNAME)
        val bundle = Bundle()
        bundle.putString(Extra_USERNAME, username)


        detailUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)

        detailUserViewModel.setDetailUsers(username.toString())
        detailUserViewModel.getDetailUsers().observe(this, {
            if (it != null) {
                binding.apply {
                    detailName.text = it.name
                    detailUsername.text = it.login
                    detailLocation.text = it.location
                    detailCompany.text = it.company
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

        val fragmetPagerAdapter = FragmentPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = fragmetPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

}