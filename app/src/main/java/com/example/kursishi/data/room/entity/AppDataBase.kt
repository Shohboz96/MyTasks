package com.example.kursishi.data.room.entity

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kursishi.app.App
import com.example.kursishi.data.models.TaskData
import com.example.kursishi.data.room.dao.TaskDao

@Database(entities = [TaskData::class],version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun taskDao():TaskDao

    companion object{
        @Volatile
        private var INSTANCE : AppDataBase? = null

        fun getDatabase(context: App):AppDataBase {
            val tempIntance = INSTANCE
            if (tempIntance != null) {
                return tempIntance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app")
                   // .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}