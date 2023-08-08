package com.example.beering

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beering.databinding.ItemReviewBinding
import com.example.naverwebtoon.data.DrinkDetail

class ReviewAdapter(private  val itemList : ArrayList<DrinkDetail>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(drinkDetail : DrinkDetail) {
            binding.itemReviewNameTv.text = drinkDetail.titleKr
            binding.drinkDetailReviewTv.text = drinkDetail.information

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

}