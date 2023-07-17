package com.example.naverwebtoon.data

import androidx.room.Entity
import androidx.room.PrimaryKey


data class DrinkCover (
        val titleKr : String,
        val titleEn : String,
        val manufacture : String,
        val id : Int,
        val img : Int
        )

