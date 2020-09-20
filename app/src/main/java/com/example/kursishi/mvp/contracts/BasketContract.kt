package com.example.kursishi.mvp.contracts

import com.example.kursishi.data.models.TaskData

interface BasketContract {
    interface Model{
        fun getAllFromRoom(deleted:Byte,view: View)
        fun updateDeleteInRoom(deleted:Byte, data: TaskData)
        fun deleteFromRoom(data: TaskData)

    }
    interface View{
        fun init(data: List<TaskData>)
        fun deleteItem(data: TaskData)

    }
    interface Presenter{
        fun init(deleted:Byte)
        fun reload(deleted: Byte,data: TaskData)
        fun deleteTask(data: TaskData)
    }
}