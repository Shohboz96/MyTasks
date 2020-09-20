package com.example.kursishi.mvp.repositories

import com.example.kursishi.app.App
import com.example.kursishi.data.models.TaskData
import com.example.kursishi.data.room.entity.AppDataBase
import com.example.kursishi.mvp.contracts.TaskContract
import java.util.*
import java.util.concurrent.Executors

class TaskRepository : TaskContract.Model {
    private val room = AppDataBase.getDatabase(App.instance).taskDao()
    val executor = Executors.newSingleThreadExecutor()

    override fun getAllFromRoom(view: TaskContract.View) {
        val now = Calendar.getInstance().timeInMillis
        executor.execute {
            val ls = room.getAllTask(0)
            for (i in ls.indices) {
                if (ls[i].taskData - now <= 0) {
                    ls[i].deleted = 4
                    room.update(ls[i])
                }
            }
            val list = room.getAllTask(0)
            view.submitList(list)

        }
    }

    override fun insertToRoom(data: TaskData) {
        executor.execute {
            room.insert(data)
        }
    }

    override fun updateInRoom(data: TaskData) {
        executor.execute {
            room.update(data)
        }
    }

    override fun updateDeleteInRoom(deleted: Byte, data: TaskData) {
        data.deleted = deleted
        executor.execute {
            room.update(data)
        }
    }
}