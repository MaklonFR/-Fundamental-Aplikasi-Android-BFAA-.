package com.maklon.fr.githubuserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.maklon.fr.githubuserapp.databinding.ActivityUserDetailsBinding
import com.maklon.fr.githubuserapp.local.SettingPreferences
import com.maklon.fr.githubuserapp.setting.SettingActivity
import com.maklon.fr.githubuserapp.setting.SettingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetails : AppCompatActivity() {

    companion object {
        const val Extra_USERNAME = "extra_username"
        const val Extra_ID ="extra_id"
        const val Extra_URL="extra_url"
    }

    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var detailUserViewModel: DetailUserViewModel

    private val myViewModel by viewModels <SettingViewModel> {
        SettingViewModel.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionbar = supportActionBar
        actionbar!!.title = "User Profile"

        myViewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val username = intent.getStringExtra(Extra_USERNAME)
        val id = intent.getIntExtra(Extra_ID, 0)
        val avatarUrl = intent.getStringExtra(Extra_URL)

        val bundle = Bundle()
        bundle.putString(Extra_USERNAME, username)


        detailUserViewModel = ViewModelProvider(
            this
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

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailUserViewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null){
                    if (count>0) {
                        binding.fabAdd.isChecked = true
                        _isChecked = true
                    } else {
                        binding.fabAdd.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.fabAdd.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                if (username != null) {
                    if (avatarUrl != null) {
                        detailUserViewModel.addFavorite(username, id, avatarUrl)
                    }
                }
            } else {
                detailUserViewModel.removeFavorite(id)
            }
            binding.fabAdd.isChecked = _isChecked
        }

        val fragmetPagerAdapter = FragmentPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = fragmetPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edSetting ->  {
                Intent (this, SettingActivity::class.java).also {
                    startActivity(it)
                   }
            }
        }
        return true
    }

}