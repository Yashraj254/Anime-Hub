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
import com.example.animerecommender.data.AnimeItem
import kotlinx.android.synthetic.main.anime_items.view.*

class AnimeAdapter(val context: Context,val listener: AnimeItemListener) :
    ListAdapter<AnimeItem, AnimeAdapter.AnimePhotosViewHolder>(DiffCallback) {


    val arrList = ArrayList<AnimeItem>()

    companion object DiffCallback : DiffUtil.ItemCallback<AnimeItem>() {
        override fun areItemsTheSame(oldItem: AnimeItem, newItem: AnimeItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AnimeItem, newItem: AnimeItem): Boolean {
            return oldItem == newItem
        }
    }

    inner   class AnimePhotosViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<ImageView>(R.id.animeImg)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimePhotosViewHolder {
        val viewHolder = AnimePhotosViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.anime_items, parent, false)
        )

        viewHolder.imageView.setOnClickListener {
            listener.onAnimeClicked(arrList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: AnimePhotosViewHolder, position: Int) {


        holder.itemView.animeTitle.text = arrList[position].animeTitle
        Glide.with(context).load(arrList[position].imgUrl).into(holder.itemView.animeImg)
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
        fun onAnimeClicked(item: AnimeItem)
    }
}
