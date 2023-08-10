package com.example.beering

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beering.databinding.ItemHomeBinding
import com.example.beering.databinding.ItemImageBinding

class ImageAdapter(private val reviewImageUrls: List<String>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = reviewImageUrls[position]
        holder.bind(imageUrl)
    }

    override fun getItemCount(): Int {
        return reviewImageUrls.size
    }

    inner class ImageViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            Glide.with(binding.imageView.context)
                .load(imageUrl)
                .into(binding.imageView)
        }
    }
}