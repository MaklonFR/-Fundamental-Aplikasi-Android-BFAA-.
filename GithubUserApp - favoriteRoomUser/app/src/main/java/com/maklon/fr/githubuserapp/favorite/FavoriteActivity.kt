package com.maklon.fr.githubuserapp.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maklon.fr.githubuserapp.*
import com.maklon.fr.githubuserapp.data.local.UserFavorite
import com.maklon.fr.githubuserapp.databinding.ActivityMainBinding
import com.maklon.fr.githubuserapp.local.SettingPreferences
import com.maklon.fr.githubuserapp.setting.SettingViewModel

class FavoriteActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UserAdapter

    private val myViewModel by viewModels <SettingViewModel> {
        SettingViewModel.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionbar = supportActionBar
        actionbar!!.title = "User Favorite"

        actionbar.setDisplayHomeAsUpEnabled(true)

        myViewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                Intent(this@FavoriteActivity, UserDetails::class.java).also {
                    it.putExtra(UserDetails.Extra_USERNAME, data.login)
                    it.putExtra(UserDetails.Extra_ID, data.id)
                    it.putExtra(UserDetails.Extra_URL, data.avatarUrl)
                    startActivity(it)
                }

            }
        })

        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUsers.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this, {
            if (it!=null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        })
    }

    private fun mapList(users: List<UserFavorite>): ArrayList<UserItem> {
        val lisUsers = ArrayList<UserItem>()
        for (user in users) {
            val userMap = UserItem (
                user.login,
                user.id,
                user.url_avatar
            )
            lisUsers.add(userMap)
        }
        return lisUsers
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}