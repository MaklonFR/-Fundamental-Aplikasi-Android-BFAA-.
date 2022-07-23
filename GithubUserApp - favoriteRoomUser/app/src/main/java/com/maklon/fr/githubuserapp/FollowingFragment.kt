package com.maklon.fr.githubuserapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maklon.fr.githubuserapp.databinding.FragmentFollowBinding

class FollowingFragment : Fragment(R.layout.fragment_follow) {

    private var oneBinding: FragmentFollowBinding? = null
    private val binding get() = oneBinding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var followingAdapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg = arguments
        username = arg?.getString(UserDetails.Extra_USERNAME).toString()
        Log.d("Username: ", username)

        oneBinding = FragmentFollowBinding.bind(view)
        followingAdapter = UserAdapter()
        followingAdapter.notifyDataSetChanged()

        followingAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                Toast.makeText(activity, "Your Following: " + data.login, Toast.LENGTH_SHORT).show()
                return
            }
        })

        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)

        binding.apply {
            rvUsersff.layoutManager = LinearLayoutManager(activity)
            rvUsersff.setHasFixedSize(true)
            rvUsersff.adapter = followingAdapter
        }

        viewModel.setListFollowing(username)
        viewModel.getFollowingUsers().observe(viewLifecycleOwner, {
            if (it != null) {
                followingAdapter.setList(it)
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