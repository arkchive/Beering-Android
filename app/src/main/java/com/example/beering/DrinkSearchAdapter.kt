package com.example.beering

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beering.databinding.ItemDrinkSearchResultBinding
import com.example.naverwebtoon.data.DrinkCover

class DrinkSearchAdapter(private  val itemList: ArrayList<DrinkCover>): RecyclerView.Adapter<DrinkSearchAdapter.ViewHolder>(){


    // 클릭시 상세페이지로
    interface OnItemClickListener{
        fun onItemClick(drinkInfo: DrinkCover)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener:OnItemClickListener){
        itemClickListener = onItemClickListener
    }


    inner class ViewHolder(val binding:ItemDrinkSearchResultBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(drinkInfo: DrinkCover){
            binding.itemDrinkSearchResultImgIv.setImageResource(drinkInfo.img)
            binding.itemDrinkSearchResultManufactureTv.text = drinkInfo.manufacture
            binding.itemDrinkSearchResultTitleKrTv.text = drinkInfo.titleKr
            binding.itemDrinkSearchResultTitleEnTv.text = drinkInfo.titleEn

            binding.itemDrinkSearchResultCl.setOnClickListener{

                // 이후 상세페이지가 완료됐을때, 구현
               // itemClickListener.onItemClick(drinkInfo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkSearchAdapter.ViewHolder {
        val binding = ItemDrinkSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrinkSearchAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

}