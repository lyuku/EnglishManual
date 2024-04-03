package tech.lyuku.englishmanual.base.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import tech.lyuku.englishmanual.base.databinding.ViewLoadingBinding

class LoadingView(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attributeSet, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    init {
        ViewLoadingBinding.inflate(LayoutInflater.from(context), this, true)
    }

}