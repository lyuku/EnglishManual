package tech.lyuku.englishmanual.data.models

import android.os.Parcel
import android.os.Parcelable
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
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(contentType)
        parcel.writeString(createAt)
        parcel.writeValue(hasContents)
        parcel.writeValue(hasPurchased)
        parcel.writeString(idBook)
        parcel.writeString(imgUrl)
        parcel.writeValue(isUnlimited)
        parcel.writeString(nameBook)
        parcel.writeString(publisher)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookItem> {
        override fun createFromParcel(parcel: Parcel): BookItem {
            return BookItem(parcel)
        }

        override fun newArray(size: Int): Array<BookItem?> {
            return arrayOfNulls(size)
        }
    }
}