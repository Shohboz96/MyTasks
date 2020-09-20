package com.example.kursishi.ui.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kursishi.ui.fragments.VazifalarTarixiFragment

class VazifalarTarixiAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity){

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        val fragment =
            VazifalarTarixiFragment()
       val bundle = Bundle()
        bundle.putInt("POS",position)
        fragment.arguments = bundle
        return fragment
    }
}