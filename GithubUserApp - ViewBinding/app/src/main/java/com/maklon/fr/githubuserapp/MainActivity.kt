package com.maklon.fr.githubuserapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvUser: RecyclerView
    private val list = ArrayList<Users> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvUser=findViewById(R.id.rv_users)
        rvUser.setHasFixedSize(true)

        list.addAll(listUsers)
        this.showRecycler();
    }

    private val listUsers : ArrayList<Users>
    @SuppressLint("Recycle")
    get () {
        val dataUsers = resources.getStringArray(R.array.username)
        val dataName = resources.getStringArray(R.array.name)
        val dataPhoto = resources.getStringArray(R.array.avatar)
        val dataLocation = resources.getStringArray(R.array.location)
        val dataRepo = resources.getStringArray(R.array.repository)
        val dataCompany = resources.getStringArray(R.array.company)
        val dataFollower = resources.getStringArray(R.array.followers)
        val dataFollowing = resources.getStringArray(R.array.following)

        val listUsers = ArrayList<Users>()

        for ( i in dataUsers.indices) {
            val user = Users (
                dataUsers[i],
                dataName[i],
                dataPhoto[i],
                dataLocation[i],
                dataRepo[i],
                dataCompany[i],
                dataFollower[i],
                dataFollowing[i]
            )
            listUsers.add(user)
        }
        return listUsers
    }

    private fun showRecycler () {
            rvUser.layoutManager = LinearLayoutManager (this)
        val listUserAdapter = ListUserAdapter (list)
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                //showSelectedHero(data)
                val intentToDetail = Intent(this@MainActivity, UserDetails::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
            }
        })
    }

}

