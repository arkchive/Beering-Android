package com.example.beering

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.beering.databinding.ItemDrinkSearchResultBinding
import com.example.naverwebtoon.data.DrinkCover

class DrinkFavoriteAdapter(private val itemList: ArrayList<DrinkCover>) :
    RecyclerView.Adapter<DrinkFavoriteAdapter.ViewHolder>() {


    // 클릭시 상세페이지로
    interface OnItemClickListener {
        fun onItemClick(drinkInfo: DrinkCover)
    }

    interface OnHeartClickListener {
        fun onButtonClick(position: Int)
    }

    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var heartClickListener: OnHeartClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }

    fun setOnHeartClickListener(listener: OnHeartClickListener) {
        heartClickListener = listener
    }


    inner class ViewHolder(val binding: ItemDrinkSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val heartOn: ImageView = itemView.findViewById(R.id.item_drink_search_result_heart_on_iv)
        val heartOff: ImageView = itemView.findViewById(R.id.item_drink_search_result_heart_off_iv)

        fun bind(drinkInfo: DrinkCover) {
            binding.itemDrinkSearchResultImgIv.setImageResource(drinkInfo.img)
            binding.itemDrinkSearchResultManufactureTv.text = drinkInfo.manufacture
            binding.itemDrinkSearchResultTitleKrTv.text = drinkInfo.titleKr
            binding.itemDrinkSearchResultTitleEnTv.text = drinkInfo.titleEn

            if (drinkInfo.isHeart) {
                binding.itemDrinkSearchResultHeartOnIv.visibility = View.VISIBLE
                binding.itemDrinkSearchResultHeartOffIv.visibility = View.INVISIBLE
            } else {
                binding.itemDrinkSearchResultHeartOnIv.visibility = View.INVISIBLE
                binding.itemDrinkSearchResultHeartOffIv.visibility = View.VISIBLE
            }


            binding.itemDrinkSearchResultCl.setOnClickListener {
                itemClickListener.onItemClick(drinkInfo)
            }


        }

        fun bindHeart(position: Int) {
            binding.itemDrinkSearchResultHeartOffIv.setOnClickListener {
                heartClickListener.onButtonClick(position)

            }

            binding.itemDrinkSearchResultHeartOnIv.setOnClickListener {
                heartClickListener.onButtonClick(position)

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DrinkFavoriteAdapter.ViewHolder {
        val binding =
            ItemDrinkSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrinkFavoriteAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
        holder.bindHeart(position)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isNotEmpty()) {
            for (payload in payloads) {
                if (payload is String) {

                    if (payload == "heartChange") {
                        if (itemList[position].isHeart) {
                            holder.heartOn.visibility = View.INVISIBLE
                            holder.heartOff.visibility = View.VISIBLE
                        } else {
                            holder.heartOn.visibility = View.VISIBLE
                            holder.heartOff.visibility = View.INVISIBLE
                        }
                        itemList[position].isHeart = !itemList[position].isHeart
                    }
                }
            }
        } else {
            // 전체 아이템을 바인딩하는 경우
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    fun setBindHeart(position: Int, bindHeart: Boolean) {
        itemList[position].BindHeart = bindHeart
    }

    override fun getItemCount(): Int = itemList.size

}