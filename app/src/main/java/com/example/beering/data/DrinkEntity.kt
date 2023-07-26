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

@Entity(tableName = "DrinkDetailTable")
data class DrinkDetail (
        val titleKr : String,
        val mainImg : Int, // 상세페이지 대표 이미지
        val information : String, // 주류 설명
        val score : Float, //별점
        val scoreCount : Int, // 별점 남긴 이용자 수
        val alcoholPercentage : Float // 도수
        ) {
        @PrimaryKey(autoGenerate = true)
        var id : Int = 0
        }

