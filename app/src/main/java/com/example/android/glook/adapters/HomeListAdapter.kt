package com.example.android.glook.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.android.glook.HomePoko
import kotlinx.android.synthetic.main.home_item_list_layout.view.*


class HomeListAdapter(val items: ArrayList<HomePoko>, val context: Context, val clickListener: (HomePoko) -> Unit): RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.itemImage.setImageResource(items.get(position).actionImage)
//        holder.itemText.text = items.get(position).actionDesc
        (holder as ViewHolder).bind(items[position], clickListener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(com.example.android.glook.R.layout.home_item_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(com.example.android.glook.R.id.list_item_image)
        val itemText: TextView = view.findViewById(com.example.android.glook.R.id.list_item_text)
//        view.setOnClickListener { clickListener(part)}

        fun bind(part: HomePoko, clickListener: (HomePoko) -> Unit) {
            itemView.list_item_text.text = part.actionDesc
            itemView.list_item_image.setImageResource(part.actionImage)
            itemView.setOnClickListener { clickListener(part) }

        }
    }

}