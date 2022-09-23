package com.example.animerecommender.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animerecommender.R
import com.example.animerecommender.data.AnimeItem
import kotlinx.android.synthetic.main.category_search_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CategorySearchAdapter(
    val context: Context,
    private val listener: AnimeItemListener,
) :
    ListAdapter<AnimeItem, CategorySearchAdapter.CategoryViewHolder>(DiffCallback) {


    private val arrList = ArrayList<AnimeItem>()

    companion object DiffCallback : DiffUtil.ItemCallback<AnimeItem>() {
        override fun areItemsTheSame(oldItem: AnimeItem, newItem: AnimeItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AnimeItem, newItem: AnimeItem): Boolean {
            return oldItem == newItem
        }
    }

    inner class CategoryViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {


        return CategoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_search_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        holder.itemView.categoryAnimeName.text = arrList[position].animeTitle
        val startDate = arrList[position].startedAt
        val endDate = arrList[position].endedAt
        var startedDate = "Nan"
        var endedDate = "Nan"
        val originalFormat = SimpleDateFormat("yyyy-MM-dd")
        if (!startDate.isNullOrEmpty() && !endDate.isNullOrEmpty()) {
            val date1 = originalFormat.parse(startDate)
            val date2 = originalFormat.parse(endDate)
            startedDate = SimpleDateFormat("dd-MM-yyyy").format(date1)
            endedDate = SimpleDateFormat("dd-MM-yyyy").format(date2)
        }
        holder.itemView.startedAt.text = "From: $startedDate"
        holder.itemView.endedAt.text = "To:   $endedDate"
        Glide.with(context).load(arrList[position].imgUrl).into(holder.itemView.categoryImage)
        holder.itemView.setOnClickListener {
            listener.onItemClicked(arrList[position])
        }

    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    fun updateList(newList: ArrayList<AnimeItem>) {
        arrList.clear()
        arrList.addAll(newList)
        notifyDataSetChanged()
    }

    interface AnimeItemListener {
        fun onItemClicked(item: AnimeItem)
    }
}
