package com.example.beering.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beering.R
import com.example.beering.databinding.ItemHomeBinding
import com.example.beering.util.ImageAdapter
import com.example.beering.util.getMemberId
import com.example.beering.util.like.ReviewLike

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
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
        holder.bindLike(position, reviews[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if(payloads.isNotEmpty()) {
            val payload = payloads[0] as String

            when(payload) {
                PAYLOAD_LIKE -> {
                    if(holder.likeDark.visibility == View.INVISIBLE){
                        holder.binding.homeLikeLightIb.visibility = View.INVISIBLE
                        holder.binding.homeLikeDarkIb.visibility = View.VISIBLE
                        holder.binding.homeUnlikeLightIb.visibility = View.VISIBLE
                        holder.binding.homeUnlikeDarkIb.visibility = View.INVISIBLE
                    } else {
                        holder.binding.homeLikeLightIb.visibility = View.VISIBLE
                        holder.binding.homeLikeDarkIb.visibility = View.INVISIBLE
                        holder.binding.homeUnlikeLightIb.visibility = View.VISIBLE
                        holder.binding.homeUnlikeDarkIb.visibility = View.INVISIBLE
                    }
                }
                PAYLOAD_DISLIKE -> {
                    if(holder.unlikeDark.visibility == View.INVISIBLE){
                        holder.binding.homeLikeLightIb.visibility = View.VISIBLE
                        holder.binding.homeLikeDarkIb.visibility = View.INVISIBLE
                        holder.binding.homeUnlikeLightIb.visibility = View.VISIBLE
                        holder.binding.homeUnlikeDarkIb.visibility = View.INVISIBLE
                    } else {
                        holder.binding.homeLikeLightIb.visibility = View.VISIBLE
                        holder.binding.homeLikeDarkIb.visibility = View.INVISIBLE
                        holder.binding.homeUnlikeLightIb.visibility = View.INVISIBLE
                        holder.binding.homeUnlikeDarkIb.visibility = View.VISIBLE
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
    inner class ViewHolder(val binding: ItemHomeBinding): RecyclerView.ViewHolder(binding.root) {
        val likeDark: ImageView = itemView.findViewById(R.id.home_like_dark_ib)
        val unlikeDark: ImageView = itemView.findViewById(R.id.home_unlike_dark_ib)

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

            if (review.isTabomed == "true") {
                binding.homeLikeLightIb.visibility = View.INVISIBLE
                binding.homeLikeDarkIb.visibility = View.VISIBLE
            } else if (review.isTabomed == "false") {
                binding.homeUnlikeLightIb.visibility = View.INVISIBLE
                binding.homeUnlikeDarkIb.visibility = View.VISIBLE
            }

        }

        fun bindLike(position:Int, review: ReviewsContent) {
            binding.homeLikeLightIb.setOnClickListener {
                likeClickListener.onButtonClick(position)
                ReviewLike(binding.root.context, getMemberId(binding.root.context), review.reviewId, isUp = true)
//                binding.homeLikeLightIb.visibility = View.INVISIBLE
//                binding.homeLikeDarkIb.visibility = View.VISIBLE
//                binding.homeUnlikeLightIb.visibility = View.VISIBLE
//                binding.homeUnlikeDarkIb.visibility = View.INVISIBLE
                notifyItemChanged(position, PAYLOAD_LIKE)

            }
            binding.homeLikeDarkIb.setOnClickListener {
                likeClickListener.onButtonClick(position)
                ReviewLike(binding.root.context, getMemberId(binding.root.context), review.reviewId, isUp = true)
//                binding.homeLikeLightIb.visibility = View.VISIBLE
//                binding.homeLikeDarkIb.visibility = View.INVISIBLE
//                binding.homeUnlikeLightIb.visibility = View.VISIBLE
//                binding.homeUnlikeDarkIb.visibility = View.INVISIBLE
                notifyItemChanged(position, PAYLOAD_LIKE)


            }
            binding.homeUnlikeLightIb.setOnClickListener {
                likeClickListener.onButtonClick(position)
                ReviewLike(binding.root.context, getMemberId(binding.root.context), review.reviewId, isUp = false)
//                binding.homeLikeLightIb.visibility = View.VISIBLE
//                binding.homeLikeDarkIb.visibility = View.INVISIBLE
//                binding.homeUnlikeLightIb.visibility = View.INVISIBLE
//                binding.homeUnlikeDarkIb.visibility = View.VISIBLE
                notifyItemChanged(position, PAYLOAD_DISLIKE)

            }
            binding.homeUnlikeDarkIb.setOnClickListener {
                likeClickListener.onButtonClick(position)
                ReviewLike(binding.root.context, getMemberId(binding.root.context), review.reviewId, isUp = false)
//                binding.homeLikeLightIb.visibility = View.VISIBLE
//                binding.homeLikeDarkIb.visibility = View.INVISIBLE
//                binding.homeUnlikeLightIb.visibility = View.VISIBLE
//                binding.homeUnlikeDarkIb.visibility = View.INVISIBLE
                notifyItemChanged(position, PAYLOAD_DISLIKE)

            }

        }

    }
}