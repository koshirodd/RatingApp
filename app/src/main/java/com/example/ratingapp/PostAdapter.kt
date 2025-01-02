package com.example.ratingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// ViewHolderオブジェクトを生成する。また、Viewに必要なデータをセットする。
class PostAdapter(private val postList: List<PostModel>): RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        // xmlを使用可能なUIに変換するためにinflateしてるらしい。
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.post_main, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int = postList.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = postList[position]
        holder.itemImageView.setImageResource(item.imageResId)
        holder.itemTextView.text = item.description
    }
}