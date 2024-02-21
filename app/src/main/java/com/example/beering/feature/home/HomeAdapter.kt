package com.example.beering.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.beering.R
import com.example.beering.databinding.ItemHomeReviewBinding
import com.example.beering.util.getMemberId
import com.example.beering.util.like.ReviewLike

class HomeAdapter(private val reviews: List<ReviewsContent>): RecyclerView.Adapter<HomeAdapter.ViewHolder>(){

    private lateinit var itemClickListener: HomeAdapter.OnItemClickListener
    private lateinit var likeClickListener: HomeAdapter.OnLikeClickListener
    private lateinit var unlikeClickListener: HomeAdapter.OnUnlikeClickListener

    private val likeCounts: MutableList<Int> = reviews.map { it.like }.toMutableList()

    companion object {
        const val PAYLOAD_LIKE = "payload_like"
        const val PAYLOAD_DISLIKE = "payload_dislike"
    }

    interface OnItemClickListener {
        fun onItemClick(review: ReviewsContent)
    }

    interface OnLikeClickListener {
        fun onLikeClick(position: Int)
    }
    interface OnUnlikeClickListener {
        fun onUnlikeClick(position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }

    fun setOnLikeClickListener(listener: OnLikeClickListener) {
        likeClickListener = listener
    }
    fun setOnUnlikeClickListener(listener: OnUnlikeClickListener){
        unlikeClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val binding = ItemHomeReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
        holder.bindLike(position, reviews[position], holder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if(payloads.isNotEmpty()) {
            val payload = payloads[0] as String
            val context = holder.itemView.context
            when(payload) {
                PAYLOAD_LIKE -> {
                    if(holder.likeButton.isSelected){
                        holder.likeButton.isSelected = false
                        holder.likeImageView.isSelected = false
                        holder.likeNumTextView.setTextColor(ContextCompat.getColor(context, R.color.beering_black))
                        holder.unlikeButton.isSelected = false
                        holder.unlikeImageView.isSelected = false
                    }else{
                        holder.likeButton.isSelected = true
                        holder.likeImageView.isSelected = true
                        holder.likeNumTextView.setTextColor(ContextCompat.getColor(context,R.color.beering_yellow))
                        holder.unlikeButton.isSelected = false
                        holder.unlikeImageView.isSelected = false
                    }
                }
                PAYLOAD_DISLIKE -> {
                    if(holder.unlikeButton.isSelected){
                        holder.likeButton.isSelected = false
                        holder.likeImageView.isSelected = false
                        holder.likeNumTextView.setTextColor(ContextCompat.getColor(context, R.color.beering_black))
                        holder.unlikeButton.isSelected = false
                        holder.unlikeImageView.isSelected = false
                    }else{
                        holder.likeButton.isSelected = false
                        holder.likeImageView.isSelected = false
                        holder.likeNumTextView.setTextColor(ContextCompat.getColor(context,R.color.beering_black))
                        holder.unlikeButton.isSelected = true
                        holder.unlikeImageView.isSelected = true
                    }
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
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
                likeButton.isSelected = true
                likeImageView.isSelected = true
                likeNumTextView.setTextColor(ContextCompat.getColor(context,R.color.beering_yellow))
                unlikeButton.isSelected = false
                unlikeImageView.isSelected = false
            } else if (review.isTabomed == "false") {
                likeButton.isSelected = false
                likeImageView.isSelected = false
                likeNumTextView.setTextColor(ContextCompat.getColor(context, R.color.beering_black))
                unlikeButton.isSelected = true
                unlikeImageView.isSelected = true
            } else {
                likeButton.isSelected = false
                likeImageView.isSelected = false
                likeNumTextView.setTextColor(ContextCompat.getColor(context, R.color.beering_black))
                unlikeButton.isSelected = false
                unlikeImageView.isSelected = false
            }

        }

        fun bindLike(position:Int, review: ReviewsContent, holder: ViewHolder) {
            val context = itemView.context
            holder.likeButton.setOnClickListener {
                if(holder.likeButton.isSelected){
                    likeCounts[position] -= 1
                    holder.likeNumTextView.text = likeCounts[position].toString()
                    likeClickListener.onLikeClick(position)
                    ReviewLike(binding.root.context, getMemberId(binding.root.context), review.reviewId, isUp = true)
                    notifyItemChanged(position, PAYLOAD_LIKE)
                }else{
                    likeCounts[position] += 1
                    holder.likeNumTextView.text = likeCounts[position].toString()
                    likeClickListener.onLikeClick(position)
                    ReviewLike(binding.root.context, getMemberId(binding.root.context), review.reviewId, isUp = true)
                    notifyItemChanged(position, PAYLOAD_LIKE)
            }

            holder.unlikeButton.setOnClickListener {
                if(holder.unlikeButton.isSelected && !holder.likeButton.isSelected){
                    holder.likeNumTextView.text = likeCounts[position].toString()
                    unlikeClickListener.onUnlikeClick(position)
                    ReviewLike(binding.root.context, getMemberId(binding.root.context), review.reviewId, isUp = false)
                    notifyItemChanged(position, PAYLOAD_DISLIKE)
                    }else if(!holder.unlikeButton.isSelected && holder.likeButton.isSelected){
                        likeCounts[position] -= 1
                        holder.likeNumTextView.text = likeCounts[position].toString()
                        unlikeClickListener.onUnlikeClick(position)
                        ReviewLike(binding.root.context, getMemberId(binding.root.context), review.reviewId, isUp = false)
                        notifyItemChanged(position, PAYLOAD_DISLIKE)
                    }else if(!holder.unlikeButton.isSelected && !holder.likeButton.isSelected){
                        holder.likeNumTextView.text = likeCounts[position].toString()
                        unlikeClickListener.onUnlikeClick(position)
                        ReviewLike(binding.root.context, getMemberId(binding.root.context), review.reviewId, isUp = false)
                        notifyItemChanged(position, PAYLOAD_DISLIKE)
                    }
                }
            }
        }
    }
}