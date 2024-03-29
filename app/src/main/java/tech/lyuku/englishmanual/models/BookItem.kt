package tech.lyuku.englishmanual.models

import com.squareup.moshi.Json

data class BookItem(
    @Json(name = "author") var author: String? = null,
    @Json(name = "content_type") var contentType: String? = null,
    @Json(name = "create_at") var createAt: String? = null,
    @Json(name = "has_contents") var hasContents: Int? = null,
    @Json(name = "has_purchased") var hasPurchased: Boolean? = null,
    @Json(name = "id_book") var idBook: String? = null,
    @Json(name = "img_url") var imgUrl: String? = null,
    @Json(name = "is_unlimited") var isUnlimited: Int? = null,
    @Json(name = "name_book") var nameBook: String? = null,
    @Json(name = "publisher") var publisher: String? = null
)