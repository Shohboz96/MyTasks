package com.example.kursishi.ui.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kursishi.data.models.TaskData
import com.example.kursishi.ui.fragments.BarchaVazifalarFragment
import com.example.kursishi.util.SingleBlock

class BarchaVazifalarPagerAdapter (activity: FragmentActivity): FragmentStateAdapter(activity) {

    private var listener:SingleBlock<TaskData>? = null
    fun setOnItemClickListener(block:SingleBlock<TaskData>){
        listener = block
    }
    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        val fragment =
            BarchaVazifalarFragment()
        val bundle = Bundle()
        bundle.putInt("POS",position)
        fragment.arguments = bundle
        return fragment
    }
}