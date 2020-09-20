package com.example.kursishi.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.example.kursishi.R
import com.example.kursishi.data.models.TaskData
import com.example.kursishi.data.models.Times
import com.example.kursishi.util.extensions.checkDate
import com.example.kursishi.util.extensions.checkTime
import com.example.kursishi.util.SingleBlock
import com.example.kursishi.util.extensions.toDatetime
import com.example.kursishi.util.extensions.toLongDate
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.leyout_add_task.*
import kotlinx.android.synthetic.main.leyout_add_task.view.*
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
class AddTasks(context: Context, actionName: String, fragmentManager: FragmentManager) :
    AlertDialog(context) {

    private var listener: SingleBlock<TaskData>? = null
    private val contentView =
        LayoutInflater.from(context).inflate(R.layout.leyout_add_task, null, false)
    private var taskData: TaskData? = null

    init {
        setView(contentView)
        contentView.buttonAddInItem.text = actionName
        contentView.buttonAddInItem.setOnClickListener {
            val dataName = contentView.inputNameTask.text.toString().trim()
            val dataTime = contentView.inputDateTask.text.toString().trim()
            val dataHashTag = contentView.inputHashTagTask.text.toString().trim()
            val dataUrgancy = contentView.inputUrgencyTask.text.toString().trim()
            val dataTextTask = contentView.inputTextTask.text.toString().trim()


            if (dataName.isEmpty()) {
                inputNameTask.error = "Enter name"
                return@setOnClickListener
            }
            val now = Calendar.getInstance().timeInMillis
            val deadlineLong = dataTime.toLongDate("dd.MM.yyyy hh:mm")
            if (dataTime.isEmpty() || deadlineLong <= now) {
                inputDateTask.error = "Enter date"
                return@setOnClickListener
            }

            if (dataTextTask.isEmpty()) {
                inputTextTask.error = "Enter text"
                return@setOnClickListener
            }
            if (dataUrgancy.isEmpty()) {
                inputUrgencyTask.error = "Enter urgency"
                return@setOnClickListener
            }
            if (dataHashTag.isEmpty()) {
                inputHashTagTask.error = "Enter hash tag"
                return@setOnClickListener
            }

            val data = taskData ?: TaskData()
            data.name = dataName
            data.hashTag = dataHashTag
            data.urgency = dataUrgancy
            data.texTask = dataTextTask
            data.taskData = deadlineLong
            listener?.invoke(data)
            dismiss()
        }
        contentView.buttonCancelInItem.setOnClickListener {
            dismiss()
        }

        contentView.inputUrgencyTask.setOnClickListener { it ->
                val popup = PopupMenu(it.context, it)
                popup.inflate(R.menu.menu_urgancy)
                popup.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_high -> {
                            inputUrgencyTask.setText("Yuqori")
                        }
                        R.id.menu_medium -> {
                            inputUrgencyTask.setText("O'rta")
                        }
                        R.id.menu_low -> {
                            inputUrgencyTask.setText("Past")
                        }
                    }
                    true
                }
                popup.show()

        }


        contentView.inputDateTask.setOnClickListener {
                val now = Calendar.getInstance()
                val dialog = DatePickerDialog.newInstance { _, year, monthOfYear, dayOfMonth ->
                    TimePickerDialog.newInstance({ _, hourOfDay, minute, second ->
                        val time = Times(
                            "$year",
                            "$monthOfYear",
                            "$dayOfMonth",
                            "$hourOfDay",
                            "$minute",
                            "$second"
                        )
                        Log.d("AAA", "time:  $time")
                        val date = Calendar.getInstance()
                        if (now.checkDate(time)) {
                            if (date.checkTime(time)) {
                                inputDateTask.error = "Noto'g'ri vaqt kiritildi"
                            } else {
                                inputDateTask.error = null
                                inputDateTask.setText(time.toPattern().toString())
                            }
                        } else {
                            inputDateTask.error = null
                            inputDateTask.setText(time.toPattern().toString())
                        }
                    }, true)
                        .apply {
                            show(fragmentManager, "")
                        }
                }
                dialog.minDate = now
                dialog.show(fragmentManager, "")

        }

    }


    fun setTaskDialog(data: TaskData) = with(contentView) {
        this@AddTasks.taskData = data
        inputNameTask.setText(data.name)
        inputDateTask.setText(data.taskData.toDatetime("dd.MM.yyyy hh:mm"))
        inputHashTagTask.setText(data.hashTag)
        inputUrgencyTask.setText(data.urgency)
        inputTextTask.setText(data.texTask)
    }

    fun setOnClickListener(block: SingleBlock<TaskData>) {
        listener = block
    }


}