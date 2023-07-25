package com.example.naverwebtoon.data

import androidx.room.Entity
import androidx.room.PrimaryKey


data class DrinkCover (
        val titleKr : String,
        val titleEn : String,
        val manufacture : String,
        val id : Int,
        var img : Int,
        var isHeart: Boolean = false,
        var BindHeart: Boolean = false
        )

