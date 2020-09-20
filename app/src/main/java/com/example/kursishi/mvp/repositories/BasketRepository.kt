package com.example.kursishi.mvp.repositories

import com.example.kursishi.app.App
import com.example.kursishi.data.models.TaskData
import com.example.kursishi.data.room.entity.AppDataBase
import com.example.kursishi.mvp.contracts.BasketContract
import java.util.concurrent.Executors

class BasketRepository : BasketContract.Model {
    private val room = AppDataBase.getDatabase(App.instance).taskDao()
    val executor = Executors.newSingleThreadExecutor()

    override fun getAllFromRoom(deleted: Byte, view: BasketContract.View) {
        executor.execute {
            val ls = room.getAllTask(deleted)
            view.init(ls)
        }
    }

    override fun updateDeleteInRoom(deleted: Byte, data: TaskData) {
        data.deleted = deleted
        executor.execute {
            room.update(data)
        }
    }

    override fun deleteFromRoom(data: TaskData) {
        executor.execute {
            room.delete(data)
        }
    }
}