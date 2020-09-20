package com.example.kursishi.ui.helper

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toIcon

class SharedPreferences (val context: Context){
   private val pref: SharedPreferences = context.getSharedPreferences("into",Context.MODE_PRIVATE)

    fun set(key:String,into:Boolean){
        pref.edit().apply(){
            putBoolean(key,into)
            apply()
        }
    }

    fun get(key: String):Boolean{
        return pref.getBoolean(key,false)
    }

    fun setHeaderUser(userName:String){
        pref.edit().apply(){
            putString("USER_NAME",userName)
            apply()
        }
    }
    fun getHeaderUser():String{
        return pref.getString("USER_NAME","Full Name")!!
    }
    fun setHeaderEmail(userName:String){
        pref.edit().apply(){
            putString("USER_EMAIL",userName)
            apply()
        }
    }
    fun getHeaderEmail():String{
       return pref.getString("USER_EMAIL","Email")!!
    }
    fun setHeaderImage(image:String){
        pref.edit().apply(){
            putString("USER_IMAGE",image)
            apply()
        }
    }
    fun getHeaderImage():String{
        return pref.getString("USER_IMAGE","")!!
    }

}