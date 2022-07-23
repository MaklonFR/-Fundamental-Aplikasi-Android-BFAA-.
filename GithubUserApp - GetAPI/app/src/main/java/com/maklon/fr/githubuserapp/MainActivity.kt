package com.maklon.fr.githubuserapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maklon.fr.githubuserapp.databinding.ActivityMainBinding
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
        lateinit var progressBar:ProgressBar
        lateinit var rvUser : RecyclerView
        lateinit var myAdapter: MyAdapter
        lateinit var linearLayoutManager: LinearLayoutManager

    private val list = ArrayList<UsersResponse> ()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.pb)
        progressBar.visibility= View.VISIBLE

        rvUser=findViewById(R.id.rv_users)
        rvUser.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        rvUser.layoutManager=linearLayoutManager


        val apiInterface = ApiService.create().getUser()

        apiInterface.enqueue( object : Callback<List<UsersResponse>>{
            override fun onResponse(call: Call<List<UsersResponse>>?, response: Response<List<UsersResponse>>?) {
                progressBar.visibility=View.INVISIBLE
                if (response?.body() != null) {
                    myAdapter = MyAdapter(baseContext, response.body()!!)
                    myAdapter.notifyDataSetChanged()
                    rvUser.adapter = myAdapter
                }
            }

            override fun onFailure(call: Call<List<UsersResponse>>?, t: Throwable) {
                progressBar.visibility=View.INVISIBLE
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}

