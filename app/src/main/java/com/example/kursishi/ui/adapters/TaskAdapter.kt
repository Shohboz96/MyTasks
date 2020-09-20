package com.example.kursishi.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.*
import com.example.kursishi.R
import com.example.kursishi.data.models.TaskData
import com.example.kursishi.util.SingleBlock
import com.example.kursishi.util.extensions.*
import kotlinx.android.synthetic.main.item_task2.view.*

import java.util.*

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    private val callback = object : SortedListAdapterCallback<TaskData>(this) {
        override fun areItemsTheSame(item1: TaskData?, item2: TaskData?): Boolean {
            return item1?.id == item2?.id
        }

        override fun compare(o1: TaskData?, o2: TaskData?): Int {
            return when {
                o1!!.taskData > o2!!.taskData -> 1
                o1.taskData == o2.taskData -> 0
                else -> -1
            }
        }

        override fun areContentsTheSame(oldItem: TaskData?, newItem: TaskData?) =
            oldItem?.id == newItem?.id &&
                    oldItem?.taskData == newItem?.taskData && oldItem?.name == newItem?.name &&
                    oldItem?.hashTag == newItem?.hashTag && oldItem?.urgency == newItem?.urgency && oldItem?.texTask == newItem?.texTask &&
                    oldItem?.deleted == newItem?.deleted
    }
    private val sortedList = SortedList(TaskData::class.java, callback)

    private var listener: SingleBlock<TaskData>? = null
    private var completeListener: SingleBlock<TaskData>? = null
    private var deleteListener: SingleBlock<TaskData>? = null
    private var editListener: SingleBlock<TaskData>? = null
    private var omenListener: SingleBlock<TaskData>? = null

    fun setOnItemClickListener(block: SingleBlock<TaskData>) {
        listener = block
    }

    fun setOnItemCompleteClickListener(block: SingleBlock<TaskData>) {
        completeListener = block
    }

    fun setOnDeleteClickListener(block: SingleBlock<TaskData>) {
        deleteListener = block
    }

    fun setOnEditClickListener(block: SingleBlock<TaskData>) {
        editListener = block
    }

    fun setOnOtmenaClickListener(block: SingleBlock<TaskData>) {
        omenListener = block
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_task2))

    override fun getItemCount(): Int = sortedList.size()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    fun submitList(data: List<TaskData>) {
        sortedList.beginBatchedUpdates()
        sortedList.replaceAll(data)
        sortedList.endBatchedUpdates()
    }


    fun deleteTask(taskData: TaskData) {
        sortedList.remove(taskData)
    }

    fun addTask(taskData: TaskData) {
        sortedList.add(taskData)
    }

    fun update(taskData: TaskData) {
        var index = -1

        for (i in 0 until itemCount) {
            if (sortedList[i].id == taskData.id) {
                index = i
                break
            }
        }
        if (index != -1)
            sortedList.updateItemAt(index, taskData)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.apply {
                setOnClickListener { listener?.invoke(sortedList[adapterPosition]) }
                button_more.setOnClickListener {
                    val popup = PopupMenu(it.context, it)
                    popup.setForceShowIcon()
                    popup.inflate(R.menu.menu_popup)
                    popup.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.menu_complete -> {
                                completeListener?.invoke(sortedList[adapterPosition])
                            }
                            R.id.menu_edit -> {
                                editListener?.invoke(sortedList[adapterPosition])
                            }
                            R.id.menu_delete -> {
                                deleteListener?.invoke(sortedList[adapterPosition])
                            }
                            R.id.menu_otmena -> {
                                omenListener?.invoke(sortedList[adapterPosition])
                            }
                        }
                        true
                    }
                    popup.show()
                }
            }
        }

        @SuppressLint("ResourceType")
        fun bind() = bindItem {
            val d = sortedList[adapterPosition]
            val now = Calendar.getInstance().timeInMillis
            name_task.text = d.name
            time_task.text = d.taskData.toDatetime()
            urgency_task.text = d.urgency
            val timeDiffrence = now.timeDifference(d.taskData)
            val color = "#F67BA5"
            layout_content.setBackgroundColor(
                when (timeDiffrence.days) {
                    0 -> {
                        name_task.setTextColor(Color.parseColor("#0C1650"))
                        content_item_task2.setBackgroundColor(Color.parseColor(color))
                        Color.parseColor(color).toDarkenColor()
                    }
                    in 1..3 -> {
                        content_item_task2.setBackgroundColor(Color.YELLOW)
                        Color.YELLOW.toDarkenColor()
                    }
                    else -> {
                        content_item_task2.setBackgroundColor(Color.GREEN)
                        Color.GREEN.toDarkenColor()
                    }
                }
            )

        }
    }


}