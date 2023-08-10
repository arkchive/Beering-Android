package com.example.naverwebtoon.data

import androidx.room.Entity


data class DrinkCover(
    val titleKr: String,
    val titleEn: String,
    val manufacture: String,
    val id: Int,
    var img: String?,
    var isHeart: Boolean = false
        )

@Entity(tableName = "DrinkDetailTable")
data class DrinkDetail (
        val titleKr : String,
        val mainImg : Int, // 상세페이지 대표 이미지
        val information : String, // 주류 설명
        val score : Float, //별점
        val scoreCount : Int, // 별점 남긴 이용자 수
        val alcoholPercentage : Float // 도수
        )


