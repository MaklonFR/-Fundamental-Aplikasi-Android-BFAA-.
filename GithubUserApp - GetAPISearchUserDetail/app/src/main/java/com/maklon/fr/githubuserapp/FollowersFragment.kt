package com.maklon.fr.githubuserapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.maklon.fr.githubuserapp.databinding.FragmentFollowBinding

class FollowersFragment: Fragment (R.layout.fragment_follow) {

    private var one_binding : FragmentFollowBinding?=null
    private val binding get()  = one_binding!!

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        one_binding= FragmentFollowBinding.bind(view)

    }
}