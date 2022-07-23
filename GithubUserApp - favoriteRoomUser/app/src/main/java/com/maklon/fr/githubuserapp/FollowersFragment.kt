package com.maklon.fr.githubuserapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.ViewModelProvider
import com.maklon.fr.githubuserapp.databinding.FragmentFollowBinding

class FollowersFragment : Fragment(R.layout.fragment_follow) {

    private var oneBinding: FragmentFollowBinding? = null
    private val binding get() = oneBinding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var followersAdapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg = arguments
        username = arg?.getString(UserDetails.Extra_USERNAME).toString()
        Log.d("Username: ", username)

        oneBinding = FragmentFollowBinding.bind(view)
        followersAdapter = UserAdapter()
        followersAdapter.notifyDataSetChanged()

        followersAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                Toast.makeText(activity, "Your Followers: " + data.login, Toast.LENGTH_SHORT).show()
                return
            }
        })

        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)

        binding.apply {
            rvUsersff.layoutManager = LinearLayoutManager(activity)
            rvUsersff.setHasFixedSize(true)
            rvUsersff.adapter = followersAdapter
        }

        viewModel.setListFollowers(username)
        viewModel.getFollowersUsers().observe(viewLifecycleOwner, {
            if (it != null) {
                followersAdapter.setList(it)
                showLoading(false)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        oneBinding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.spinKit.visibility = View.VISIBLE
        } else {
            binding.spinKit.visibility = View.GONE
        }
    }
}
