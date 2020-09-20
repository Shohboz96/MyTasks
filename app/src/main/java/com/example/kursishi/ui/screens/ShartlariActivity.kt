package com.example.kursishi.ui.screens

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kursishi.R
import com.example.kursishi.util.extensions.changeStatusBarColor
import kotlinx.android.synthetic.main.activity_shartlari.*

class ShartlariActivity : AppCompatActivity() {
    private var swit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shartlari)

        switchBtn.setOnClickListener {
            swit = if (swit) {
                switchBtn.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_24)
                false
            } else {
                switchBtn.setImageResource(R.drawable.ic_baseline_check_box_24)
                true
            }
        }

        buttonShartlariNext.setOnClickListener {
            if(swit){
                startActivity(Intent(this,TaskActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this,"Iltimos foydalanish shartlariga roziligingizni bildiruvchi tugmani bosing",Toast.LENGTH_LONG).show()
            }
        }
    }
}
