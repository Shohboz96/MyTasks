package com.example.kursishi.mvp.repositories

import com.example.kursishi.app.App
import com.example.kursishi.data.models.AllListData
import com.example.kursishi.data.models.TaskData
import com.example.kursishi.data.room.entity.AppDataBase
import com.example.kursishi.mvp.contracts.AllTaskContract
import java.util.concurrent.Executors

class AllTaskRepository : AllTaskContract.Model{
    private val room = AppDataBase.getDatabase(App.instance).taskDao()
    val executor = Executors.newSingleThreadExecutor()

    override fun getDataFromRoom(d1: Byte, view: AllTaskContract.View) {
        executor.execute {
            val ls = room.getAllTask(d1)
            view.submitList(ls)
        }
    }

    override fun deleteFromRoom(data: TaskData) {
        executor.execute {
            room.delete(data)
        }
    }

    override fun updateDeleteInRoom(deleted: Byte, data: TaskData) {
        executor.execute {
        data.deleted = deleted
            room.update(data)
        }
    }

    override fun updateToRoom(data: TaskData) {
        executor.execute {
        room.update(data)
        }
    }
}