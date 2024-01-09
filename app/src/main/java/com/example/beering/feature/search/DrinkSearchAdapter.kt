package com.example.beering.feature.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beering.R
import com.example.beering.util.like.DrinkLike
import com.example.beering.util.getMemberId
import com.example.beering.databinding.ItemDrinkSearchResultBinding
import com.example.beering.util.data.DrinkCover

class DrinkSearchAdapter(private val itemList: ArrayList<DrinkCover>) :
    RecyclerView.Adapter<DrinkSearchAdapter.ViewHolder>() {


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


    inner class ViewHolder(val binding: ItemDrinkSearchResultBinding) : RecyclerView.ViewHolder(binding.root) {

        val heartOn: ImageView = binding.itemDrinkSearchResultHeartOnIv
        val heartOff: ImageView = binding.itemDrinkSearchResultHeartOffIv
        val binding_temp: ItemDrinkSearchResultBinding = binding
        lateinit var drinkInfo_temp: DrinkCover

        fun bind(drinkInfo: DrinkCover) {
            Glide.with(binding.root.context)
                .load(drinkInfo.img) // 불러올 이미지 url
                .placeholder(R.drawable.img_temp_drink) // 이미지 로딩 시작하기 전 표시할 이미지
                .error(R.drawable.img_temp_drink) // 로딩 에러 발생 시 표시할 이미지
                .fallback(R.drawable.img_temp_drink) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                .into(binding.itemDrinkSearchResultImgIv)

            binding.itemDrinkSearchResultManufactureTv.text = drinkInfo.manufacture
            binding.itemDrinkSearchResultTitleKrTv.text = drinkInfo.titleKr
            binding.itemDrinkSearchResultTitleEnTv.text = drinkInfo.titleEn

            binding.itemDrinkSearchResultCl.setOnClickListener {

                itemClickListener.onItemClick(drinkInfo)
            }

            if (drinkInfo.isHeart) {
                binding.itemDrinkSearchResultHeartOnIv.visibility = View.VISIBLE
                binding.itemDrinkSearchResultHeartOffIv.visibility = View.INVISIBLE
            } else {
                binding.itemDrinkSearchResultHeartOnIv.visibility = View.INVISIBLE
                binding.itemDrinkSearchResultHeartOffIv.visibility = View.VISIBLE
            }


        }

        fun bindHeart(position: Int, drinkInfo: DrinkCover) {
            binding.itemDrinkSearchResultHeartOffIv.setOnClickListener {
                heartClickListener.onButtonClick(position)
                drinkInfo_temp = drinkInfo
            }

            binding.itemDrinkSearchResultHeartOnIv.setOnClickListener {
                heartClickListener.onButtonClick(position)
                drinkInfo_temp = drinkInfo

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemDrinkSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
        holder.bindHeart(position,itemList[position])

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isNotEmpty()) {
            for (payload in payloads) {
                if (payload is String) {

                    if (payload == "heartChange") {
                        itemList[position].isHeart = !itemList[position].isHeart
                        if (itemList[position].isHeart) {
                            holder.heartOn.visibility = View.VISIBLE
                            holder.heartOff.visibility = View.INVISIBLE
                        } else {
                            holder.heartOn.visibility = View.INVISIBLE
                            holder.heartOff.visibility = View.VISIBLE
                        }
                        DrinkLike(holder.binding_temp.root.context, getMemberId(holder.binding_temp.root.context), holder.drinkInfo_temp.id)

                    }
                }
            }
        } else {
            // 전체 아이템을 바인딩하는 경우
            super.onBindViewHolder(holder, position, payloads)
        }
    }


    fun clearItems() {
        itemList.clear() // 기존 아이템 제거
        notifyDataSetChanged() // 데이터 변경 알림
    }

    override fun getItemCount(): Int = itemList.size

}