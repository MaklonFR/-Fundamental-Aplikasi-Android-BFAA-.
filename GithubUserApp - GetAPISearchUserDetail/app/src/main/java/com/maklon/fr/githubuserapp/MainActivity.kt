package com.maklon.fr.githubuserapp

import android.app.SearchManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maklon.fr.githubuserapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var userviewModel : UserViewModel
    private lateinit var adapter: UserAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

            adapter = UserAdapter()
            adapter.notifyDataSetChanged()

            adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
                override fun onItemClicked(data: userItem) {
                    Intent ( this@MainActivity, UserDetails::class.java).also {
                        it.putExtra(UserDetails.Extra_USERNAME, data.login)
                        startActivity(it)
                    }

                }
            })
            userviewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

            binding.apply {
                rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
                rvUsers.setHasFixedSize(true)
                rvUsers.adapter=adapter
            }

            this.userviewModel.getSetUsers().observe(this, {
                if (it !=null) {
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
            /*
            Gunakan method ini ketika search selesai atau OK
             */
            override fun onQueryTextSubmit(myQuery: String): Boolean {
                //Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                //searchView.clearFocus()
                searchUser(myQuery)
                return true
            }

            /*
            Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
             */
            override fun onQueryTextChange(newText: String): Boolean {
                searchUser(newText)
                return false
            }
        })
        return true
    }

    private fun searchUser (edquery: String) {
       // var edquery : TextView = findViewById(R.id.edSearch)
        binding.apply {
            val query = edquery
            if (query.isEmpty()) return
            showLoading(true)
            userviewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pb.visibility = View.VISIBLE
        } else {
            binding.pb.visibility = View.GONE
        }
    }
}

