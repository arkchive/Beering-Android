package com.example.beering.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beering.util.ImageAdapter
import com.example.beering.R
import com.example.beering.databinding.ItemHomeBinding

class HomeAdapter(private val reviews: List<ReviewsContent>): RecyclerView.Adapter<HomeAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int {
        return reviews.size
    }
    inner class ViewHolder(val binding: ItemHomeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ReviewsContent) {
            binding.itemHomePostNameTv.text = review.nickName
            binding.itemHomePostDateTv.text = review.diffFromCurrentTime

            Glide.with(binding.root)
                .load(review.profileImage)
                .placeholder(R.drawable.img_default_profile)
                .error(R.drawable.img_default_profile)
                .fallback(R.drawable.img_default_profile)
                .circleCrop()
                .into(binding.itemHomePostProfileIv)

            binding.homeLikeTv.text = review.like.toString()
            binding.homeUnlikeTv.text = review.dislike.toString()

            val imageAdapter = ImageAdapter(review.reviewImageUrls)
            binding.itemHomeImageRv.adapter = imageAdapter
            binding.itemHomeImageRv.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

            binding.itemHomePostContentTv.text = review.content
        }
    }
}