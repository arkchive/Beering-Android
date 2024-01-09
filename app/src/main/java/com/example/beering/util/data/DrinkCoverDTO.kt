package com.example.beering.util.data

data class DrinkCover(
    val titleKr: String,
    val titleEn: String,
    val manufacture: String,
    val id: Int,
    var img: String?,
    var isHeart: Boolean = false
)

data class DrinkCoverResponse(
    val isSuccess: Boolean,
    val responseCode: Int,
    val responseMessage: String,
    val result: DrinkCoverResult
)

data class DrinkCoverResult(
    val content: List<Content>,
    val page: Int,
    val last: Boolean

)

data class Content(
    val drinkId: Int,
    val nameKr: String,
    val nameEn: String,
    val manufacturer: String,
    val imageUrlList: List<String>,
    val isLiked: Boolean
)

data class DrinkFavoriteResponse(
    val isSuccess: Boolean,
    val responseCode: Int,
    val responseMessage: String,
    val result: DrinkFavoriteResult
)

data class DrinkFavoriteResult(
    val content: List<FavoirteContent>,
    val page: Int,
    val last: Boolean

)

data class FavoirteContent(
    val drinkId: Int,
    val nameKr: String,
    val nameEn: String,
    val manufacturer: String,
    val primaryImageUrl: String,
    val isLiked: Boolean
)

