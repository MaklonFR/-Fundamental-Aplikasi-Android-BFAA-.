package com.maklon.fr.githubuserapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maklon.fr.githubuserapp.databinding.ActivityMainBinding
import com.maklon.fr.githubuserapp.favorite.FavoriteActivity
import com.maklon.fr.githubuserapp.local.SettingPreferences
import com.maklon.fr.githubuserapp.setting.SettingActivity
import com.maklon.fr.githubuserapp.setting.SettingViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import java.util.*


@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    private val viewModel by viewModels <SettingViewModel> {
        SettingViewModel.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                Intent(this@MainActivity, UserDetails::class.java).also {
                    it.putExtra(UserDetails.Extra_USERNAME, data.login)
                    it.putExtra(UserDetails.Extra_ID, data.id)
                    it.putExtra(UserDetails.Extra_URL, data.avatarUrl)
                    startActivity(it)
                }

            }
        })
        userViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[UserViewModel::class.java]

        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter
        }

        this.userViewModel.getSetUsers().observe(this, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.edSearch).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(newText: String): Boolean {
                searchUser(newText)
                return false
            }

            override fun onQueryTextChange(myQuery: String): Boolean {

                val job = GlobalScope.launch(Default) {
                    repeat(1)
                    {
                        delay(100)
                    }
                }

                runBlocking {
                    delay(1000)
                    job.cancel()
                    if (myQuery != "") {
                        searchUser(myQuery)
                    }
                }
                return false
            }
        })
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edFavorite ->  {
                Intent (this,FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.edSetting ->  {
                Intent (this,SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.edSearch -> {

             }
        }
        return true
    }

    private fun searchUser(edQuery: String) {
        binding.apply {
            val Qr = edQuery
            if (Qr.isEmpty()) return
            showLoading(true)
            userViewModel.setSearchUsers(Qr)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.spinKit.visibility = View.VISIBLE
        } else {
            binding.spinKit.visibility = View.GONE
        }
    }


}



