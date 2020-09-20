package com.example.kursishi.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kursishi.R
import com.example.kursishi.app.App
import com.example.kursishi.data.models.TaskData
import com.example.kursishi.data.room.entity.AppDataBase
import com.example.kursishi.mvp.contracts.BasketContract
import com.example.kursishi.mvp.presenters.BasketPresenter
import com.example.kursishi.mvp.presenters.TaskPresenter
import com.example.kursishi.mvp.repositories.BasketRepository
import com.example.kursishi.mvp.repositories.TaskRepository
import com.example.kursishi.ui.adapters.VazifalarSavatchasiAdapter
import kotlinx.android.synthetic.main.activity_vazifalar_savatchasi.*
import java.util.concurrent.Executors

class VazifalarSavatchasiActivity : AppCompatActivity(),BasketContract.View {
    private val adapter = VazifalarSavatchasiAdapter()
    private lateinit var presenter: BasketPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vazifalar_savatchasi)

        setSupportActionBar(toolbar3)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = BasketPresenter(this,BasketRepository())

        listVazifalarSavatchasi.adapter = adapter
        listVazifalarSavatchasi.setHasFixedSize(true)
        listVazifalarSavatchasi.layoutManager = LinearLayoutManager(this)

        presenter.init(2)
        adapter.setOnItemQaytaTiklashClickListener {presenter.reload(0,it) }
        adapter.setOnDeleteClickListener {presenter.deleteTask(it)}
        adapter.setOnItemClickListener {
            startActivity(Intent(this, ShowActivity::class.java).putExtra("note", it))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun init(data: List<TaskData>) {
        runOnUiThread {
        adapter.submitList(data)
        }
    }

    override fun deleteItem(data: TaskData) {
        runOnUiThread {
            adapter.deleteTask(data)
        }
    }
}
