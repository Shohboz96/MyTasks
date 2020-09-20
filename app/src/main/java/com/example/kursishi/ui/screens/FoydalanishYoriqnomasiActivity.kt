package com.example.kursishi.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kursishi.R
import com.example.kursishi.data.models.YoriqnomaData
import com.example.kursishi.ui.adapters.YoriqnomaAdapter
import kotlinx.android.synthetic.main.activity_foydalanish_yoriqnomasi.*

class FoydalanishYoriqnomasiActivity : AppCompatActivity() {
    private val data = ArrayList<YoriqnomaData>()
    private lateinit var adapter: YoriqnomaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foydalanish_yoriqnomasi)
        val toolbar: Toolbar = findViewById(R.id.toolbaryo)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadData()

        adapter = YoriqnomaAdapter(data)
        listYoriqnoma.adapter = adapter
        listYoriqnoma.layoutManager = LinearLayoutManager(this)

    }
    private fun loadData() {
        val images = arrayOf(R.drawable.main,R.drawable.add,R.drawable.main2,R.drawable.header,R.drawable.menu_asosiy,R.drawable.menu_ulashish)
        data.add(YoriqnomaData(images[0],"Bu yerdan yangi vazifalarni qo'shish oynasi"))
        data.add(YoriqnomaData(images[1],"Bu yerdan yangi vazifalarni qo'shishingiz mumkin"))
        data.add(YoriqnomaData(images[2],"Bu yerda asosiy menu joylashgan"))
        data.add(YoriqnomaData(images[4],"Bu asosiy menu bu yerdan kerakli oynani tanlashingiz mumkin"))
        data.add(YoriqnomaData(images[3],"Bu yerda o'zingiz haqingizdagi ma'lumotlarni (rasm, ism, email) kiritishingiz mumkin. Buning uchun ularni ustiga bosishingiz kifoya "))
        data.add(YoriqnomaData(images[5],"Bu yerdan dasturni do'stlariningizga ulashishingiz mumkin"))

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if ( item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
