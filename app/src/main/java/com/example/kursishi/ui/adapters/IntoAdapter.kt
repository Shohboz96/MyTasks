package com.example.kursishi.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kursishi.R
import com.example.kursishi.data.models.IntoData
import kotlinx.android.synthetic.main.item_page.view.*

class IntoAdapter(val data: List<IntoData>) : RecyclerView.Adapter<IntoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_page,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {
            itemView.apply {
                text_item.text = data[adapterPosition].title
                intoImage.setImageResource(data[adapterPosition].imageIrl)
               // layoutContent.setBackgroundColor(data[adapterPosition].color)
            }
        }
    }
}