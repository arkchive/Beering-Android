package com.example.beering.feature.my.likeReviews

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

class LikeReviewsAdapter (private val reviews: List<ReviewsContent>): RecyclerView.Adapter<LikeReviewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = ItemHomeReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
    }



    override fun getItemCount(): Int {
        return reviews.size
    }

    inner class ViewHolder(val binding: ItemHomeReviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ReviewsContent) {
            binding.itemHomeReviewNicknameTv.text = review.nickName
            binding.itemHomeReviewTimeTv.text = review.diffFromCurrentTime

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