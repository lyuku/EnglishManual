package tech.lyuku.englishmanual.base.core.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
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

@BindingAdapter("srcDrawable", requireAll = false)
fun ImageView.bindSrcDrawable(
    @DrawableRes srcDrawable: Int,
) {
    this.setImageResource(srcDrawable)
}