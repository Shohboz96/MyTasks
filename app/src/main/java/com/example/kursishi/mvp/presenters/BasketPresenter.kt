package com.example.kursishi.mvp.presenters

import com.example.kursishi.data.models.TaskData
import com.example.kursishi.mvp.contracts.BasketContract

class BasketPresenter (private val view:BasketContract.View, private val model:BasketContract.Model): BasketContract.Presenter{
    override fun init(deleted:Byte) {
        model.getAllFromRoom(deleted,view)
    }

    override fun reload(deleted: Byte, data: TaskData) {
        model.updateDeleteInRoom(deleted, data)
        view.deleteItem(data)
    }

    override fun deleteTask(data: TaskData) {
        model.deleteFromRoom(data)
        view.deleteItem(data)
    }
}