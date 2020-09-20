package com.example.kursishi.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kursishi.R
import com.example.kursishi.data.models.YoriqnomaData
import com.example.kursishi.ui.adapters.YoriqnomaAdapter
import kotlinx.android.synthetic.main.activity_foydalanish_shartlari.*

class FoydalanishShartlariActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foydalanish_shartlari)
        val toolbar: Toolbar = findViewById(R.id.toolbarsha)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if ( item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
