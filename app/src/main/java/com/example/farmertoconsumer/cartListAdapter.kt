package com.example.farmertoconsumer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cartlayout.view.*

class cartListAdapter(var cartListItem: List<CartModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class  imageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(cartModel: CartModel){
            itemView.image_post_title.text = cartModel.name

            itemView.price.text = cartModel.price

            Glide.with(itemView.context).load(cartModel.image).into(itemView.image_post_image)
            itemView.image_post_image.clipToOutline=true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cartlayout,parent, false)
        return imageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  cartListItem.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as imageViewHolder).bind(cartListItem[position])

    }
}
