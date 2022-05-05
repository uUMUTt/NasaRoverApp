package com.example.nasaroverapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val _fragmentList = ArrayList<Fragment>()
    val fragmentList: ArrayList<Fragment>
        get() = _fragmentList

    override fun getItemCount(): Int {
        return _fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return _fragmentList[position]
    }

    fun addFragment(fragment: Fragment) {
        _fragmentList.add(fragment)
    }


}