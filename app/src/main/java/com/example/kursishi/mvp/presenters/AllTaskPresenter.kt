package com.example.kursishi.mvp.presenters

import com.example.kursishi.data.models.TaskData
import com.example.kursishi.mvp.contracts.AllTaskContract


class AllTaskPresenter(private val model:AllTaskContract.Model, private val view: AllTaskContract.View): AllTaskContract.Presenter{

    override fun loadData(d1: Byte) {
        model.getDataFromRoom(d1, view)
    }

    override fun delateTask(data: TaskData) {
        model.deleteFromRoom(data)
        view.deleteItem(data)
    }

    override fun deleteItem(deleted: Byte, data: TaskData) {
        model.updateDeleteInRoom(deleted, data)
        view.itemDelete(data)
    }

    override fun openDialogDeleteItem(data: TaskData) {
        view.openDialogItemDelete(data)
    }

    override fun openEditDialog(data: TaskData) {
        view.openDialogEdit(data)
    }

    override fun editItem(data: TaskData) {
        model.updateToRoom(data)
        view.itemEdit(data)
    }

    override fun updateDeleteItem(deleted: Byte, data: TaskData) {
        model.updateDeleteInRoom(deleted, data)
        view.deleteItem(data)
    }
}