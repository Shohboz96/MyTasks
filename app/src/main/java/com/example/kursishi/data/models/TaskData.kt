package com.example.kursishi.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class TaskData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var taskData:Long = 0,
    var name: String = "",
    var hashTag: String = "",
    var urgency: String = "",
    var texTask: String = "",
    var deleted: Byte = 0
):Serializable