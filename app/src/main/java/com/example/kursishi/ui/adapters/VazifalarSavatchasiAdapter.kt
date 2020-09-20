package com.example.kursishi.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.example.kursishi.R
import com.example.kursishi.data.models.TaskData
import com.example.kursishi.util.SingleBlock
import com.example.kursishi.util.extensions.bindItem
import com.example.kursishi.util.extensions.inflate
import com.example.kursishi.util.extensions.setForceShowIcon
import com.example.kursishi.util.extensions.toDatetime
import kotlinx.android.synthetic.main.item_task2.view.*


class VazifalarSavatchasiAdapter : RecyclerView.Adapter<VazifalarSavatchasiAdapter.ViewHolder>() {
    private var qaytaTiklashListener: SingleBlock<TaskData>? = null
    private var listener:SingleBlock<TaskData>? = null
    fun setOnItemClickListener(block:SingleBlock<TaskData>){
        listener = block
    }

    private val callback = object : SortedListAdapterCallback<TaskData>(this) {
        override fun areItemsTheSame(item1: TaskData?, item2: TaskData?) = item1?.id == item2?.id

        override fun compare(o1: TaskData?, o2: TaskData?): Int {
           return when {
               o1!!.taskData > o2!!.taskData -> 1
               o1.taskData == o2.taskData -> 0
               else -> -1
           }

        }

        override fun areContentsTheSame(oldItem: TaskData?, newItem: TaskData?) = oldItem == newItem
    }
    val sortedlist = SortedList(TaskData::class.java, callback)
    private var deleteListener: SingleBlock<TaskData>? = null


    fun setOnItemQaytaTiklashClickListener(block: SingleBlock<TaskData>) {
        qaytaTiklashListener = block
    }

    fun setOnDeleteClickListener(block: SingleBlock<TaskData>) {
        deleteListener = block
    }


    fun submitList(data: List<TaskData>) {
        sortedlist.beginBatchedUpdates()
        sortedlist.replaceAll(data)
        sortedlist.endBatchedUpdates()
    }

    fun deleteTask(taskData: TaskData){
        sortedlist.remove(taskData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_task2))

    override fun getItemCount() = sortedlist.size()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.apply {
                setOnClickListener {
                    listener?.invoke(sortedlist[adapterPosition])
                }
                button_more.setOnClickListener {
                    val popup = PopupMenu(it.context, it)
                    popup.setForceShowIcon()
                    popup.inflate(R.menu.menu_vazifalr_savatchasi)
                    popup.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.qayta_tiklash -> {
                                qaytaTiklashListener?.invoke(sortedlist[adapterPosition])
                            }
                            R.id.butunlay_ochirish -> {
                                deleteListener?.invoke(sortedlist[adapterPosition])
                            }
                        }
                        true
                    }
                    popup.show()
                }
            }
        }

        fun bind() = bindItem {
            val d = sortedlist[adapterPosition]
            itemView.apply {
                name_task.text = d.name
                time_task.text = d.taskData.toDatetime()
                layout_content.visibility = View.GONE
                //hashTag_task.text = d.hashTag
                   urgency_task.setText(d.urgency)
                //   text_task.text = d.texTask
            }
        }

    }

}