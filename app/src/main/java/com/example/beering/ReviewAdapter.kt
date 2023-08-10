package com.example.beering

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beering.api.ReviewPreview
import com.example.beering.databinding.ItemReviewBinding
import com.example.naverwebtoon.data.DrinkDetail

class ReviewAdapter(private  val itemList : List<ReviewPreview>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    inner class ViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(review: ReviewPreview) {
            binding.itemReviewNameTv.text = review.nickname
            binding.drinkDetailReviewTv.text = review.content
            binding.itemReviewDateTv.text = review.createAt
            val reviewRating = review.totalRating
            updateReviewRating(reviewRating)

            Glide.with(binding.root)
                .load(review.profile_image_url)
                .placeholder(R.drawable.img_default_profile)
                .error(R.drawable.img_default_profile)
                .fallback(R.drawable.img_default_profile)
                .circleCrop()
                .into(binding.itemReviewProfileIv)
        }

        private fun updateReviewRating(rating : Float){
            if(rating == 0.0f){
                //빈 아이콘이 디폴트값
            } else if(rating > 0.0f && rating < 1.0f){
                binding.reviewStar1HalfIv.visibility = View.VISIBLE
            }else if(rating == 1.0f || rating > 1.0f){
                binding.reviewStar1FullIv.visibility = View.VISIBLE

                if(rating > 1.0f && rating < 2.0f){
                    binding.reviewStar2HalfIv.visibility = View.VISIBLE
                }else if(rating == 2.0f || rating > 2.0f){
                    binding.reviewStar2FullIv.visibility = View.VISIBLE

                    if(rating > 2.0f && rating < 3.0f){
                        binding.reviewStar3HalfIv.visibility = View.VISIBLE
                    }else if(rating == 3.0f || rating > 3.0f){
                        binding.reviewStar3FullIv.visibility = View.VISIBLE

                        if(rating > 3.0f && rating < 4.0f){
                            binding.reviewStar4HalfIv.visibility = View.VISIBLE
                        }else if(rating == 4.0f || rating > 4.0f){
                            binding.reviewStar4FullIv.visibility = View.VISIBLE

                            if(rating > 4.0f && rating < 5.0f){
                                binding.reviewStar5HalfIv.visibility = View.VISIBLE
                            }else if(rating == 5.0f || rating > 5.0f){
                                binding.reviewStar5FullIv.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }


        }
    }
}
