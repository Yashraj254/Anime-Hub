package com.example.animerecommender.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animerecommender.R
import com.example.animerecommender.data.CategoryGrid
import kotlinx.android.synthetic.main.anime_items.view.*
import kotlinx.android.synthetic.main.grid_item.view.*

class GridViewAdapter(val arrList: ArrayList<CategoryGrid>, val context: Context,val listener: CategoryGridListener) :
    ListAdapter<CategoryGrid, GridViewAdapter.AnimeGridViewHolder>(DiffCallback) {





    companion object DiffCallback : DiffUtil.ItemCallback<CategoryGrid>() {
        override fun areItemsTheSame(oldItem: CategoryGrid, newItem: CategoryGrid): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CategoryGrid, newItem: CategoryGrid): Boolean {
            return oldItem == newItem
        }
    }

    inner   class AnimeGridViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<ImageView>(R.id.gridImage)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeGridViewHolder {
        val viewHolder = AnimeGridViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        )

        viewHolder.imageView.setOnClickListener {
            listener.onItemClicked(arrList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: AnimeGridViewHolder, position: Int) {


        holder.itemView.gridText.text = arrList[position].category
        Glide.with(context).load(arrList[position].imageUrl).into(holder.itemView.gridImage)
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    fun updateList(newList: ArrayList<CategoryGrid>) {
        arrList.clear()
        arrList.addAll(newList)
        notifyDataSetChanged()
    }

    interface CategoryGridListener {
        fun onItemClicked(item: CategoryGrid)
    }



}
