package com.example.kursishi.ui.screens

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.kursishi.*
import com.example.kursishi.ui.adapters.IntoAdapter

import com.example.kursishi.ui.helper.SharedPreferences
import com.example.kursishi.data.models.IntoData
import com.example.kursishi.util.extensions.changeNavigationBarColor
import com.example.kursishi.util.extensions.changeStatusBarColor
import com.example.kursishi.util.extensions.toDarkenColor
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_into.*

class IntoActivity : AppCompatActivity() {
    private lateinit var adapter: IntoAdapter
    private val data = ArrayList<IntoData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_into)
        changeStatusBarColor(Color.GRAY)

        loadData()

        adapter = IntoAdapter(data)
        pager.adapter = adapter


        buttonNext.setOnClickListener {
            if (pager.currentItem != data.size - 1){
                pager.setCurrentItem(pager.currentItem + 1,true)
            }else{
                val pref = SharedPreferences(this)
                pref.set("aaa",true)
                startActivity(Intent(this, ShartlariActivity::class.java))
                finish()
            }
        }
    }

    private fun loadData() {
        data.add(
            IntoData(
                "Kundalik  hayotda ko'pgina vazifalar bajaramizga to'gri keladi \nba'zida ular bilan bog'liq ba'zi muammolarga duch kelamiz: \n ularni unutib qo'yamiz, \n vazifalar bilan bog'liq ma'lumotlar yodimizdan ko'tariladi \n va hokazo ",
                R.drawable.ic_task,
                Color.parseColor(getString(R.string.intoColor1))
            )
        )
        data.add(
            IntoData(
                "Ularni qayd etishning oson yo'li mavjud. Qanday deysizmi? Biz bunga ko'maklashamiz. Shunchaki ularni avvaldan rejalashtirib kiritib qo'ying.",
                R.drawable.ic_to_do_list,
                Color.parseColor(getString(R.string.intoColor2))
            )
        )
        data.add(
            IntoData(
                "Va bu ilova sizga barchasi eslatib, \nma'lumot berib turadi va siz ularni:" +
                        "\n- o'zgartirishinigiz\n- bekor qilishingiz\n- o'chirib yuborishingiz\n- do'stlaringiz bilan ulashishingiz\n- va boshqa ko'plab imkoniyatlarga ega bolishingiz mumkin.",
                R.drawable.ic_list,
                Color.parseColor(getString(R.string.intoColor3))
            )
        )

    }
}
