package com.example.wishlistapp

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WishlistAdapter(private val wishlistItems: ArrayList<WishlistItem>) :
    RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    inner class WishlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.itemPriceTextView)
        private val urlTextView: TextView = itemView.findViewById(R.id.itemUrlTextView)


        fun bind(wishlistItem: WishlistItem) {
            nameTextView.text = wishlistItem.name
            priceTextView.text = wishlistItem.price
            urlTextView.text = wishlistItem.url

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wishlistItem.url))
                try {
                    itemView.context.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    wishlistItems.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, wishlistItems.size)
                }
                true // Return true to indicate the long press was handled
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wishlist_item, parent, false)
        return WishlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val currentItem = wishlistItems[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return wishlistItems.size
    }
}