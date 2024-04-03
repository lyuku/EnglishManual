package tech.lyuku.englishmanual.data.models

import com.squareup.moshi.Json

data class BookCategoryList(
    @Json(name = "top_category_list") var topCategoryList: List<TopCategory>? = null
)