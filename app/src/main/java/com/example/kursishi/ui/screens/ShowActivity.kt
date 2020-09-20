package com.example.kursishi.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.kursishi.R
import com.example.kursishi.data.models.TaskData
import com.example.kursishi.util.extensions.toDatetime
import kotlinx.android.synthetic.main.activity_show.*
import java.util.*

class ShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        val toolbar: Toolbar = findViewById(R.id.toolbar_detals)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val bundle = intent.extras!!

        val note = intent.getSerializableExtra("note") as TaskData
        val now = Calendar.getInstance().timeInMillis
        date.text = note.taskData.toDatetime()
        title_text.text = note.name
        text_task_show.text = note.texTask

        coutdownView_show.start(note.taskData - now)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)

    }
}
