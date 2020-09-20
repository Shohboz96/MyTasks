package com.example.kursishi.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.kursishi.R
import com.example.kursishi.app.App
import com.example.kursishi.data.models.AllListData
import com.example.kursishi.data.room.entity.AppDataBase
import com.example.kursishi.ui.adapters.BarchaVazifalarPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_barcha_vazifalar.*

class BarchaVazifalarActivity : AppCompatActivity() {
    var list = ArrayList<AllListData>()
    private lateinit var adapter:BarchaVazifalarPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcha_vazifalar)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = BarchaVazifalarPagerAdapter(this)
        pagerBarchaVazifalar.adapter = adapter

        TabLayoutMediator(tablayoutBarchaVazifalar,pagerBarchaVazifalar){ tab, position ->
            when(position){
                0 ->{tab.text = "Eskirgan"}
                1 ->{tab.text = "Bajarilgan"}
                2 ->{tab.text = "Bekor qilingan"}
                3 ->{tab.text = "Vaqti kelmagan"}
            }
        }.attach()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if ( item.itemId == android.R.id.home) finish()
        startActivity(Intent(this,TaskActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

    }
}
