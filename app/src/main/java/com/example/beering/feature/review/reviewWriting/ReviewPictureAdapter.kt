package com.example.beering.feature.review.reviewWriting

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beering.databinding.ItemPictureBinding

class ReviewPictureAdapter(private val imageList: List<Uri>) :
    RecyclerView.Adapter<ReviewPictureAdapter.ViewHolder>() {



    interface OnCancelClickListener {
        fun onCancelClick(imageUri: Uri)
    }

    private lateinit var cancelClickListener: OnCancelClickListener

    fun setOnCancelClickListener(onCancelClickListener: OnCancelClickListener) {
        cancelClickListener = onCancelClickListener
    }

    inner class ViewHolder(val binding: ItemPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUri : Uri) {
            Glide.with(binding.pictureIv.context)
                .load(imageUri)
                .fitCenter()
                .into(binding.pictureIv)

            binding.cancelIv.setOnClickListener {
                cancelClickListener.onCancelClick(imageUri)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position])

    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}