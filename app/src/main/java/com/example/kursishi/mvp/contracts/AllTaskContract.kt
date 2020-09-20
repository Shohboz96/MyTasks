package com.example.kursishi.mvp.contracts

import com.example.kursishi.data.models.TaskData

interface AllTaskContract {
    interface Model{
        fun getDataFromRoom(d1:Byte,view: View)
        fun deleteFromRoom(data: TaskData)
        fun updateDeleteInRoom(deleted: Byte, data: TaskData)
        fun updateToRoom(data: TaskData)
    }

    interface View{
        fun submitList(data: List<TaskData>)
        fun deleteItem(data: TaskData)
        fun openDialogItemDelete(data: TaskData)
        fun itemDelete(data: TaskData)
        fun openDialogEdit(data: TaskData)
        fun itemEdit(data: TaskData)
    }
    interface Presenter{
        fun loadData(d1:Byte)
        fun delateTask(data: TaskData)
        fun openDialogDeleteItem(data: TaskData)
        fun deleteItem(deleted:Byte,data: TaskData)
        fun openEditDialog(data: TaskData)
        fun editItem(data: TaskData)
        fun updateDeleteItem(deleted: Byte,data: TaskData)
    }
}