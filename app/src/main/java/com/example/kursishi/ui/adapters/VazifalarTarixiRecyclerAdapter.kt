package com.example.kursishi.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kursishi.R
import com.example.kursishi.data.models.TaskData
import com.example.kursishi.util.SingleBlock
import com.example.kursishi.util.extensions.bindItem
import com.example.kursishi.util.extensions.inflate
import com.example.kursishi.util.extensions.toDatetime
import kotlinx.android.synthetic.main.item_task2.view.*


class VazifalarTarixiRecyclerAdapter: RecyclerView.Adapter<VazifalarTarixiRecyclerAdapter.ViewHolder>(){
    private val ls = ArrayList<TaskData>()
    private var listener:SingleBlock<TaskData>? = null
    fun setOnItemClickListener(block:SingleBlock<TaskData>){
        listener = block
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(parent.inflate(R.layout.item_task2))
    override fun getItemCount(): Int = ls.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int)  = holder.bind()

    fun submitList(data: List<TaskData>){
        ls.clear()
        ls.addAll(data)
        notifyItemRangeRemoved(0,ls.size)
    }
    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view){

        init {
            itemView.setOnClickListener {
                listener?.invoke(ls[adapterPosition])
            }
        }

        fun bind() = bindItem{
            val d = ls[adapterPosition]
            itemView.apply {
                name_task.text = d.name
                time_task.text = d.taskData.toDatetime()
                button_more.visibility = View.GONE
                layout_content.visibility = View.GONE
                urgency_task.text = d.urgency
            }
        }
    }
}