package com.example.kursishi.mvp.contracts

import android.content.Context
import com.example.kursishi.data.models.TaskData

interface TaskContract {
    interface Model{
        fun insertToRoom(data: TaskData)
        fun updateInRoom(data: TaskData)
        fun updateDeleteInRoom(deleted:Byte, data: TaskData)
        fun getAllFromRoom(view: View)

    }
    interface View{
        fun openDialogAdd()
        fun taskAdd(data: TaskData)
        fun drawerOpen()
        fun openDialogEdit(data: TaskData)
        fun taskEdit(data: TaskData)
        fun openDialogDelete(data: TaskData)
        fun taskDelete(data: TaskData)
        fun taskOtmena(data: TaskData)
        fun itemClick(data: TaskData)
        fun loadDataHeader()
        fun welcome()
        fun welcomeOne()
        fun submitList(data: List<TaskData>)
        fun sendApk(context: Context)
        fun navigationItemSelected(itemId:Any)

    }
    interface Presenter{
        fun init()
        fun openAddDialog()
        fun addTask(data:TaskData)
        fun openDrawer()
        fun openEditDialog(data: TaskData)
        fun edittask(data: TaskData)
        fun openDeleteDialog(data: TaskData)
        fun deleteTask(deleted:Byte,data: TaskData)
        fun otmenaTask(deleted:Byte,data: TaskData)
        fun clickItem(data: TaskData)
        fun loadHeaderData()
        fun welcome()
        fun welcomeOne()
        fun sendApk(context: Context)
        fun navigationItemSelected(itemId:Any)
    }

}