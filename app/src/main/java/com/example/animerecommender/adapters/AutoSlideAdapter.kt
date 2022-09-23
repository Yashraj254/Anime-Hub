package com.example.animerecommender.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.animerecommender.R
import com.example.animerecommender.data.AnimeItem
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.slider_image_container.view.*


class AutoSlideAdapter(
   val context: Context,
    val listener: OnSlideClickListener
    ) : SliderViewAdapter<AutoSlideAdapter.Holder?>() {
    private var images= ArrayList<AnimeItem>()


    class Holder(itemView: View) :
        SliderViewAdapter.ViewHolder(itemView) {
    }
    override fun onBindViewHolder(viewHolder: Holder?, position: Int) {
        viewHolder?.itemView?.imageViewForSlider?.let {
            viewHolder.itemView.setOnClickListener {
                listener.onSlideClick(images[position])
            }
            Glide.with(context).load(images[position].imgUrl)
                .into(it)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.slider_image_container, parent, false)
        )
    }

    fun updateList(newList: ArrayList<AnimeItem>){
        images.clear()
        images.addAll(newList)
        notifyDataSetChanged()
    }
    override fun getCount(): Int {
        return images.size
    }

    interface OnSlideClickListener {
        fun onSlideClick(item: AnimeItem)
    }
}