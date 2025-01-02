package com.example.ratingapp

import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// RecyclerViewのアイテム1つごとにViewAdapterによってインスタンス化(アイテムごとにMainViewHolderが1対1で紐付けられる)される。
class PostViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    // RecyclerViewで使用するパーツを定義する。
    val itemImageView: ImageView = itemView.findViewById(R.id.post_image_view)
    val itemTextView: TextView = itemView.findViewById(R.id.post_text_view)
}