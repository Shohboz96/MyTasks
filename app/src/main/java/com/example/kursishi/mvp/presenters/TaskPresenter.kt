package com.example.kursishi.mvp.presenters

import android.content.Context
import com.example.kursishi.data.models.TaskData
import com.example.kursishi.mvp.contracts.TaskContract

class TaskPresenter(private val model: TaskContract.Model, private val view: TaskContract.View):
    TaskContract.Presenter{
    override fun openAddDialog() {
        view.openDialogAdd()
    }

    override fun addTask(data: TaskData) {
        model.insertToRoom(data)
        view.taskAdd(data)
    }

    override fun openDrawer() {
        view.drawerOpen()
    }

    override fun openEditDialog(data: TaskData) {
        view.openDialogEdit(data)
    }

    override fun edittask(data: TaskData) {
        model.updateInRoom(data)
        view.taskEdit(data)
    }

    override fun openDeleteDialog(data: TaskData) {
        view.openDialogDelete(data)
    }

    override fun deleteTask(deleted:Byte,data: TaskData) {
        model.updateDeleteInRoom(deleted, data)
        view.taskDelete(data)
    }

    override fun otmenaTask(deleted: Byte, data: TaskData) {
        model.updateDeleteInRoom(deleted, data)
        view.taskOtmena(data)
    }

    override fun clickItem(data: TaskData) {
        view.itemClick(data)
    }

    override fun loadHeaderData() {
        view.loadDataHeader()
    }

    override fun welcome() {
        view.welcome()
    }

    override fun welcomeOne() {
        view.welcomeOne()
    }

    override fun init() {
        model.getAllFromRoom(view)
    }

    override fun sendApk(context: Context) {
        view.sendApk(context)
    }

    override fun navigationItemSelected(itemId: Any) {
        view.navigationItemSelected(itemId)
    }
}