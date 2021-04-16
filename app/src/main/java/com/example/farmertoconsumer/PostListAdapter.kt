package com.example.farmertoconsumer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_with_image.view.*

class PostListAdapter(var postListItem: List<PostModel>, val clickListener: (PostModel) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class  imageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(postModel: PostModel, clickListener: (PostModel) -> Unit){
            itemView.image_post_title.text = postModel.name
            itemView.desc_post_desc.text = postModel.desc
            itemView.price.text = postModel.price

            Glide.with(itemView.context).load(postModel.image).into(itemView.image_post_image)
            itemView.image_post_image.clipToOutline=true

            itemView.setOnClickListener {
                clickListener(postModel)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_with_image,parent, false)
        return imageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  postListItem.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as imageViewHolder).bind(postListItem[position], clickListener)
    }
}