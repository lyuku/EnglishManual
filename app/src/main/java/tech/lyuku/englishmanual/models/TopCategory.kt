package tech.lyuku.englishmanual.models

import com.squareup.moshi.Json

data class TopCategory(
    @Json(name = "id_top_category") var idTopCategory: String? = null,
    @Json(name = "is_focused") var isFocused: Boolean? = null,
    @Json(name = "name_category") var nameCategory: String? = null,
    @Json(name = "sub_category_list") var subCategoryList: List<SubCategory>? = null
)