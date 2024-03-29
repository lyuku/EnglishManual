package tech.lyuku.englishmanual.core.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

@BindingAdapter("srcUrl", "circleCrop", "placeholder", requireAll = false)
fun ImageView.bindSrcUrl(
    url: String?,
    circleCrop: Boolean = false,
    placeholder: Drawable?,
) {
    this.load(url) {
        crossfade(true)
        placeholder(placeholder)
        if (circleCrop) {
            transformations(CircleCropTransformation())
        }

    }
}
