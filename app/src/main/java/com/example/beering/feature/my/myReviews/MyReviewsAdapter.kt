package com.example.beering.feature.my.myReviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beering.util.ImageAdapter
import com.example.beering.R
import com.example.beering.feature.home.ReviewsContent
import com.example.beering.databinding.ItemHomeBinding
import com.example.beering.databinding.ItemHomeReviewBinding
import com.example.beering.databinding.ItemMyReviewsBinding

class MyReviewsAdapter(private val reviews: List<ReviewsContent>): RecyclerView.Adapter<MyReviewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMyReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int {
        return reviews.size
    }


    inner class ViewHolder(val binding: ItemMyReviewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ReviewsContent) {
            binding.itemMyReviewsNicknameTv.text = review.nickName
            binding.itemMyReviewsTimeTv.text = review.diffFromCurrentTime
   

//            Glide.with(binding.root)
//                .load(review.profileImage)
//                .placeholder(R.drawable.img_default_profile)
//                .error(R.drawable.img_default_profile)
//                .fallback(R.drawable.img_default_profile)
//                .circleCrop()
//                .into(binding.itemHomePostProfileIv)


        }
    }

}