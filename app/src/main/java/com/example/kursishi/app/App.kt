package com.example.kursishi.app

import android.app.Application

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object{
        lateinit var instance:App
    }
}