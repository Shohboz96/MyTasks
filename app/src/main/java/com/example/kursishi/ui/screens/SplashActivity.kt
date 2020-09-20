package com.example.kursishi.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.example.kursishi.R
import com.example.kursishi.ui.helper.SharedPreferences
import java.util.concurrent.Executors

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_splash)

        Executors.newSingleThreadExecutor().execute {
            Thread.sleep(1500)
            val pref = SharedPreferences(this)
            if(pref.get("aaa")){
                startActivity(Intent(this, TaskActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, IntoActivity::class.java))
                finish()
            }
        }
    }
}
