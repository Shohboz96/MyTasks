package com.example.kursishi.ui.adapters

import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.example.kursishi.R
import com.example.kursishi.data.models.TaskData
import com.example.kursishi.util.SingleBlock
import com.example.kursishi.util.extensions.*
import kotlinx.android.synthetic.main.item_task2.view.*

import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.*

class BarchaVazifalarRecyclerAdapter(var pos: Int) : RecyclerView.Adapter<BarchaVazifalarRecyclerAdapter.ViewHolder>(){

    private var listener:SingleBlock<TaskData>? = null
    private var cloneListener : SingleBlock<TaskData>? = null
    private var deleteListener : SingleBlock<TaskData>? = null
    private var editListener : SingleBlock<TaskData>? = null
    private var complateListener : SingleBlock<TaskData>? = null
    private var otmenaListener : SingleBlock<TaskData>? = null
    private var newDeleteListener : SingleBlock<TaskData>? = null
    fun setOnItemClickListener(block:SingleBlock<TaskData>){
        listener = block
    }

    fun setOnItemCloneListener(block: SingleBlock<TaskData>){
        cloneListener = block
    }
    fun setOnItemDeleteListener(block: SingleBlock<TaskData>){
        deleteListener = block
    }
    fun setOnItemEditListener(block: SingleBlock<TaskData>){
        editListener = block
    }
    fun setOnItemComplateeListener(block: SingleBlock<TaskData>){
        complateListener = block
    }
    fun setOnItemOtmenaListener(block: SingleBlock<TaskData>){
        otmenaListener = block
    }
    fun setOnItemNewDeleteListener(block: SingleBlock<TaskData>){
        newDeleteListener = block
    }

    private val callback = object : SortedListAdapterCallback<TaskData>(this){
        override fun areItemsTheSame(item1: TaskData?, item2: TaskData?) = item1!!.id == item2!!.id

        override fun compare(o1: TaskData?, o2: TaskData?): Int {
            return when {
                o1!!.taskData > o2!!.taskData -> 1
                o1.taskData == o2.taskData -> 0
                else -> -1
            }

        }

        override fun areContentsTheSame(oldItem: TaskData?, newItem: TaskData?) = oldItem == newItem
    }
    val sortedList = SortedList(TaskData::class.java, callback)

    fun submitList(data: List<TaskData>){
        sortedList.beginBatchedUpdates()
        sortedList.replaceAll(data)
        sortedList.endBatchedUpdates()
    }
    fun deleteTask(taskData: TaskData){
        sortedList.remove(taskData)
    }
    fun update(taskData: TaskData){
        val index = sortedList.indexOf(taskData)
        sortedList.updateItemAt(index, taskData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)  = ViewHolder(parent.inflate(R.layout.item_task2))

    override fun getItemCount() = sortedList.size()

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        if(pos == 3){
           holder.fourPopup()
        }else{
            holder.twoPopup()
        }
    }

    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                listener?.invoke(sortedList[adapterPosition])
            }
        }

         private fun bind2() = bindItem {
            val d = sortedList[adapterPosition]
            itemView.apply {
                name_task.text = d.name
             //   time_task.tim = d.taskData.toDatetime()
                layout_content.visibility = View.GONE
                urgency_task.text = d.urgency
            }
        }

        private fun bind() = bindItem {
            val d = sortedList[adapterPosition]
            itemView.apply {
                val now = Calendar.getInstance().timeInMillis
                val timeDiffrence = now.timeDifference(d.taskData)
                val color = "#F67BA5"
                layout_content.setBackgroundColor(
                    when(timeDiffrence.days){
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
                    })
                name_task.text = d.name
                time_task.text = d.taskData.toDatetime()
                urgency_task.text = d.urgency
            }
        }

        fun fourPopup() {
            bind()
            itemView.button_more.setOnClickListener {
                val popup = PopupMenu(it.context, it)
                popup.setForceShowIcon()
                popup.inflate(R.menu.menu_popup)
                popup.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_complete -> {
                            complateListener?.invoke(sortedList[adapterPosition])
                        }
                        R.id.menu_delete -> {
                            newDeleteListener?.invoke(sortedList[adapterPosition])
                        }
                        R.id.menu_otmena -> {
                            otmenaListener?.invoke(sortedList[adapterPosition])
                        }
                        R.id.menu_edit -> {
                            editListener?.invoke(sortedList[adapterPosition])
                        }
                    }
                    true
                }
                popup.show()
            }
        }

        fun twoPopup() {
            bind2()
            itemView.button_more.setOnClickListener { it ->
                val popup = PopupMenu(it.context, it)
                popup.setForceShowIcon()
                popup.inflate(R.menu.menu_clone)
                popup.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_clone -> {
                            cloneListener?.invoke(sortedList[adapterPosition])
                        }
                        R.id.menu_delete -> {
                            deleteListener?.invoke(sortedList[adapterPosition])
                        }
                    }
                    true
                }
                popup.show()
            }
        }
    }
}
