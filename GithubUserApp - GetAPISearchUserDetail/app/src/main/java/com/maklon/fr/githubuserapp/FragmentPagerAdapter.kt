package com.maklon.fr.githubuserapp

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter


class FragmentPagerAdapter(private val myFragment: Context, fm:FragmentManager): FragmentPagerAdapter (fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitle = intArrayOf(R.string.myTabOne, R.string.myTabTwo)

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment ()
            1 -> fragment = FollowingFragment ()
        }
        return  fragment as  Fragment
    }

    override fun  getPageTitle (position: Int): CharSequence? {
        return myFragment.resources.getString(tabTitle[position])
    }

}