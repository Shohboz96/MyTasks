package com.example.kursishi.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.kursishi.data.models.TaskData

@Dao
interface BaseDao<T> {
    @Update
    fun update(data: TaskData)

    @Delete
    fun delete(data: TaskData)

    @Insert
    fun insert(data: TaskData):Long
}