package com.maklon.fr.githubuserapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maklon.fr.githubuserapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                Intent(this@MainActivity, UserDetails::class.java).also {
                    it.putExtra(UserDetails.Extra_USERNAME, data.login)
                    startActivity(it)
                }

            }
        })
        userViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)

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

                val job = GlobalScope.launch(Dispatchers.Default) {
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

    private fun searchUser(edQuery: String) {
        binding.apply {
            val qr = edQuery
            if (qr.isEmpty()) return
            showLoading(true)
            userViewModel.setSearchUsers(qr)
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



