package com.example.kursishi.ui.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.kursishi.R
import com.example.kursishi.app.App
import com.example.kursishi.data.models.AllListData
import com.example.kursishi.data.models.TaskData
import com.example.kursishi.data.room.entity.AppDataBase
import com.example.kursishi.mvp.contracts.AllTaskContract
import com.example.kursishi.mvp.presenters.AllTaskPresenter
import com.example.kursishi.mvp.repositories.AllTaskRepository
import com.example.kursishi.ui.adapters.BarchaVazifalarRecyclerAdapter
import com.example.kursishi.ui.dialogs.AddTasks
import com.example.kursishi.ui.screens.ShowActivity
import com.example.kursishi.ui.screens.TaskActivity
import kotlinx.android.synthetic.main.fragment_barcha_vazifalar.*
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList


class BarchaVazifalarFragment : Fragment(),AllTaskContract.View {
    private lateinit var adapter: BarchaVazifalarRecyclerAdapter
    var list = ArrayList<AllListData>()
    private val db by lazy { AppDataBase.getDatabase(App.instance) }
    private val taskDao by lazy { db.taskDao() }
    val executor = Executors.newSingleThreadExecutor()

    private lateinit var presenter: AllTaskPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_barcha_vazifalar, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        val position = bundle.getInt("POS")
        adapter = BarchaVazifalarRecyclerAdapter(position)
        listBarchaVazifalar.adapter = adapter
        listBarchaVazifalar.layoutManager = LinearLayoutManager(activity)
        presenter = AllTaskPresenter(AllTaskRepository(),this)
        presenter.loadData(
            when(position){
                0-> 4
                1-> 3
                2-> 1
                else -> 0 })

        adapter.apply {
            setOnItemClickListener {
                startActivity(Intent(context, ShowActivity::class.java).putExtra("note", it))
            }

            setOnItemDeleteListener {presenter.delateTask(it) }

            setOnItemCloneListener { presenter.openDialogDeleteItem(it) }
            setOnItemEditListener { presenter.openEditDialog(it) }
            setOnItemNewDeleteListener {presenter.updateDeleteItem(2,it) }
            setOnItemOtmenaListener {presenter.updateDeleteItem(1,it) }
            setOnItemComplateeListener {presenter.updateDeleteItem(3,it) }
        }

    }

    override fun submitList(data: List<TaskData>) {
        activity?.runOnUiThread {
            adapter.submitList(data)
        }
    }

    override fun deleteItem(data: TaskData) {
        activity?.runOnUiThread {
            adapter.deleteTask(data)
        }
    }

    override fun itemDelete(data: TaskData) {
        activity?.runOnUiThread {
            adapter.deleteTask(data)
        }
        startActivity(Intent(App.instance,TaskActivity::class.java))
        activity!!.finish()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun openDialogItemDelete(data: TaskData) {
        val dialog = AddTasks(context!!, "Qaytarish", fragmentManager!!)
        dialog.setTaskDialog(data)
        dialog.setOnClickListener {presenter.deleteItem(0,it) }
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun openDialogEdit(data: TaskData) {
        val dialog = AddTasks(context!!, "Yangilash", fragmentManager!!)
        dialog.setTaskDialog(data)
        dialog.setOnClickListener {presenter.editItem(it) }
        dialog.show()
    }

    override fun itemEdit(data: TaskData) {
        activity?.runOnUiThread {
            adapter.update(data)
        }
    }
}
