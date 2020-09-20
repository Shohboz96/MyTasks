package com.example.kursishi.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.kursishi.R
import com.example.kursishi.app.App
import com.example.kursishi.data.models.AllListData
import com.example.kursishi.data.models.TaskData
import com.example.kursishi.data.room.entity.AppDataBase
import com.example.kursishi.ui.adapters.VazifalarTarixiRecyclerAdapter
import com.example.kursishi.ui.screens.ShowActivity
import com.example.kursishi.util.SingleBlock
import kotlinx.android.synthetic.main.fragment_vazifalar_tarixi.*
import java.util.concurrent.Executors

class VazifalarTarixiFragment : Fragment() {
    val list = ArrayList<AllListData>()

    private var listener: SingleBlock<TaskData>? = null
    private lateinit var adapter: VazifalarTarixiRecyclerAdapter
    private val db by lazy { AppDataBase.getDatabase(App.instance) }
    private val taskDao by lazy { db.taskDao() }
    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vazifalar_tarixi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        val position = bundle.getInt("POS")
        adapter = VazifalarTarixiRecyclerAdapter()

        executor.execute {
            list.add(AllListData(taskDao.getAllTask(3)))
            list.add(AllListData(taskDao.getAllTask(1)))
            list.add(AllListData(taskDao.getAllTask(4)))
            activity?.runOnUiThread {
                adapter.submitList(list[position].list)
            }
        }
        listVazifalarTarixiFragment.adapter = adapter
        listVazifalarTarixiFragment.layoutManager = LinearLayoutManager(App.instance)

        adapter.setOnItemClickListener {
            startActivity(Intent(context, ShowActivity::class.java).putExtra("note", it))
        }
    }


}
