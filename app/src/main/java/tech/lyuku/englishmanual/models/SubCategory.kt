package tech.lyuku.englishmanual.models

import com.squareup.moshi.Json

data class SubCategory(
    @Json(name = "book_list") var bookList: List<BookItem>? = null,
    @Json(name = "id_category") var idCategory: String? = null,
    @Json(name = "is_ranking") var isRanking: Boolean? = null,
    @Json(name = "name_category") var nameCategory: String? = null,
    @Json(name = "need_load_more") var needLoadMore: Boolean? = null
)