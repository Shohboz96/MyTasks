package com.example.kursishi.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.kursishi.data.models.TaskData

@Dao
interface TaskDao : BaseDao<TaskData>{
    @Query("select * from TaskData where deleted=:id")
    fun getAllTask(id:Byte):List<TaskData>
}