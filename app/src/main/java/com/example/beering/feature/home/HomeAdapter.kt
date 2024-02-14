package com.example.beering.feature.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beering.R
import com.example.beering.databinding.ItemHomeBinding
import com.example.beering.databinding.ItemHomeReviewBinding
import com.example.beering.util.ImageAdapter
import com.example.beering.util.getMemberId
import com.example.beering.util.like.ReviewLike
import kotlinx.coroutines.NonDisposableHandle.parent

class HomeAdapter(private val reviews: List<ReviewsContent>): RecyclerView.Adapter<HomeAdapter.ViewHolder>(){

    private lateinit var itemClickListener: HomeAdapter.OnItemClickListener
    private lateinit var likeClickListener: HomeAdapter.OnLikeClickListener

    companion object {
        const val PAYLOAD_LIKE = "payload_like"
        const val PAYLOAD_DISLIKE = "payload_dislike"
    }

    interface OnItemClickListener {
        fun onItemClick(review: ReviewsContent)
    }

    interface OnLikeClickListener {
        fun onButtonClick(position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }

    fun setOnLikeClickListener(listener: OnLikeClickListener) {
        likeClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val binding = ItemHomeReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
        holder.bindLike(position, reviews[position], holder)
        updateLikeButtonUI(holder, reviews[position])
        updateUnlikeButtonUI(holder, reviews[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if(payloads.isNotEmpty()) {
            val payload = payloads[0] as String

            when(payload) {
                PAYLOAD_LIKE -> {
                    updateLikeButtonUI(holder, reviews[position])
                }
                PAYLOAD_DISLIKE -> {
                    updateUnlikeButtonUI(holder, reviews[position])
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    private fun updateLikeButtonUI(holder:ViewHolder, review: ReviewsContent){
        val context = holder.itemView.context
        val isAlreadyLiked = review.isTabomed == "true"

        if(isAlreadyLiked){
            holder.likeButton.setBackgroundResource(R.drawable.bg_home_review_btn_unclicked)
            holder.likeImageView.setBackgroundResource(R.drawable.ic_like_light)
            holder.likeNumTextView.setTextColor(ContextCompat.getColor(context, R.color.beering_black))
            holder.unlikeButton.setBackgroundResource(R.drawable.bg_home_review_btn_unclicked)
            holder.unlikeImageView.setBackgroundResource(R.drawable.ic_unlike_light)
        }else {
            holder.likeButton.setBackgroundResource(R.drawable.bg_home_review_btn_clicked)
            holder.likeImageView.setBackgroundResource(R.drawable.ic_like_dark)
            holder.likeNumTextView.setTextColor(ContextCompat.getColor(context,R.color.beering_yellow))
            holder.unlikeButton.setBackgroundResource(R.drawable.bg_home_review_btn_unclicked)
            holder.unlikeImageView.setBackgroundResource(R.drawable.ic_unlike_light)
        }
    }

    private fun updateUnlikeButtonUI(holder: ViewHolder, review: ReviewsContent) {
        val context = holder.itemView.context
        val isAlreadyUnliked = review.isTabomed == "false"

        if(isAlreadyUnliked){
            holder.likeButton.setBackgroundResource(R.drawable.bg_home_review_btn_unclicked)
            holder.likeImageView.setBackgroundResource(R.drawable.ic_like_light)
            holder.likeNumTextView.setTextColor(ContextCompat.getColor(context,R.color.beering_black))
            holder.unlikeButton.setBackgroundResource(R.drawable.bg_home_review_btn_unclicked)
            holder.unlikeImageView.setBackgroundResource(R.drawable.ic_unlike_light)
        }else {
            holder.likeButton.setBackgroundResource(R.drawable.bg_home_review_btn_unclicked)
            holder.likeImageView.setBackgroundResource(R.drawable.ic_like_light)
            holder.likeNumTextView.setTextColor(ContextCompat.getColor(context, R.color.beering_black))
            holder.unlikeButton.setBackgroundResource(R.drawable.bg_home_review_btn_clicked)
            holder.unlikeImageView.setBackgroundResource(R.drawable.ic_unlike_dark)
        }
    }

    override fun getItemCount(): Int {
        return reviews.size
    }
    inner class ViewHolder(val binding: ItemHomeReviewBinding): RecyclerView.ViewHolder(binding.root) {
        val likeButton: ConstraintLayout = binding.itemHomeReviewLikeCl
        val likeImageView: ImageView = binding.itemHomeReviewLikeIv
        val likeNumTextView: TextView = binding.itemHomeReviewLikeNumTv
        val unlikeButton : ConstraintLayout = binding.itemHomeReviewUnlikeCl
        val unlikeImageView: ImageView = binding.itemHomeReviewUnlikeIv

        fun bind(review: ReviewsContent) {
            val context = ViewHolder(binding).itemView.context
            binding.itemHomeReviewNicknameTv.text = review.nickName
            binding.itemHomeReviewTimeTv.text = review.diffFromCurrentTime

//            Glide.with(binding.root)
//                .load(review.profileImage)
//                .placeholder(R.drawable.img_default_profile)
//                .error(R.drawable.img_default_profile)
//                .fallback(R.drawable.img_default_profile)
//                .circleCrop()
//                .into(binding.itemHomeReviewProfileIv)

            binding.itemHomeReviewLikeNumTv.text = review.like.toString()

//            val imageAdapter = ImageAdapter(review.reviewImageUrls)
//            binding.itemHomeImageRv.adapter = imageAdapter
//            binding.itemHomeImageRv.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

            binding.itemHomeReviewContentTv.text = review.content

            if (review.isTabomed == "true") {
                likeButton.setBackgroundResource(R.drawable.bg_home_review_btn_clicked)
                likeImageView.setBackgroundResource(R.drawable.ic_like_dark)
                likeNumTextView.setTextColor(ContextCompat.getColor(context,R.color.beering_yellow))
                unlikeButton.setBackgroundResource(R.drawable.bg_home_review_btn_unclicked)
                unlikeImageView.setBackgroundResource(R.drawable.ic_unlike_light)
            } else if (review.isTabomed == "false") {
                likeButton.setBackgroundResource(R.drawable.bg_home_review_btn_unclicked)
                likeImageView.setBackgroundResource(R.drawable.ic_like_light)
                likeNumTextView.setTextColor(ContextCompat.getColor(context, R.color.beering_black))
                unlikeButton.setBackgroundResource(R.drawable.bg_home_review_btn_clicked)
                unlikeImageView.setBackgroundResource(R.drawable.ic_unlike_dark)
            } else {
                likeButton.setBackgroundResource(R.drawable.bg_home_review_btn_unclicked)
                likeImageView.setBackgroundResource(R.drawable.ic_like_light)
                likeNumTextView.setTextColor(ContextCompat.getColor(context, R.color.beering_black))
                unlikeButton.setBackgroundResource(R.drawable.bg_home_review_btn_unclicked)
                unlikeImageView.setBackgroundResource(R.drawable.ic_unlike_light)
            }

        }

        fun bindLike(position:Int, review: ReviewsContent, holder: ViewHolder) {
            holder.likeButton.setOnClickListener {
                likeClickListener.onButtonClick(position)
                ReviewLike(binding.root.context, getMemberId(binding.root.context), review.reviewId, isUp = true)
                updateLikeButtonUI(holder, review)
                notifyItemChanged(position, PAYLOAD_LIKE)

            }

            holder.unlikeButton.setOnClickListener{
                likeClickListener.onButtonClick(position)
                ReviewLike(binding.root.context, getMemberId(binding.root.context), review.reviewId, isUp = false)
                updateUnlikeButtonUI(holder, review)
                notifyItemChanged(position, PAYLOAD_DISLIKE)
            }

        }

    }
}