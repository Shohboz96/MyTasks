package com.example.kursishi.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.kursishi.R
import com.example.kursishi.ui.adapters.VazifalarTarixiAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_vazifalar_tarixi.*

class VazifalarTarixiActivity : AppCompatActivity() {
    private lateinit var adapter: VazifalarTarixiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vazifalar_tarixi)

        setSupportActionBar(toolbar2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = VazifalarTarixiAdapter(this)

        pagerVazifalarTarixi.adapter = adapter

        TabLayoutMediator(tablayoutVazifalarTarixi,pagerVazifalarTarixi){ tab, position ->
            when(position){
                0 ->{tab.text = "Bajarilgan"}
                1 ->{tab.text = "Bekor qilingan"}
                2 ->{tab.text = "Vaqti o'tgan"}
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
