package com.example.beering

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beering.databinding.ItemHomeBinding

class ImageAdapter(private val reviewImageUrls: List<String>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(reviewImageUrls)
    }

    override fun getItemCount(): Int {
        return reviewImageUrls.size
    }

    inner class ImageViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewImageUrls: List<String>) {
            val imageRecyclerView = binding.itemHomeImageRv
            val imageLayoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            val imageAdapter = ImageAdapter(reviewImageUrls)
            imageRecyclerView.layoutManager = imageLayoutManager
            imageRecyclerView.adapter = imageAdapter
        }
    }
}