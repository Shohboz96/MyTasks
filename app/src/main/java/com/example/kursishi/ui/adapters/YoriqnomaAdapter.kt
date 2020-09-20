package com.example.kursishi.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kursishi.R
import com.example.kursishi.data.models.YoriqnomaData
import com.example.kursishi.util.extensions.bindItem
import com.example.kursishi.util.extensions.inflate
import kotlinx.android.synthetic.main.item_yoriqnoma.view.*

class YoriqnomaAdapter(val list:ArrayList<YoriqnomaData>):RecyclerView.Adapter<YoriqnomaAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.item_yoriqnoma))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  = holder.bind()

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind() = bindItem {
            val d = list[adapterPosition]
            itemView.apply {
                imageYoriqnoma.setImageResource(d.imageUrl)
                textYoriqnoma.text = d.yoriqnomaText
            }

        }
    }
}