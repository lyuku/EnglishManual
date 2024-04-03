package tech.lyuku.englishmanual.base.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import tech.lyuku.englishmanual.base.databinding.ViewEmptyBinding

class EmptyView(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attributeSet, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    private var viewBinding: ViewEmptyBinding

    init {
        viewBinding = ViewEmptyBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun showEmpty(message: String? = null) {
        message?.let {
            viewBinding.tvEmptyMessage.text = it
        }
        visibility = VISIBLE
    }

    fun hideEmpty() {
        visibility = GONE
    }

    fun setOnRetry(listener: () -> Unit) {
        viewBinding.btRetry.visibility = VISIBLE
        viewBinding.btRetry.setOnClickListener {
            listener.invoke()
        }
    }
}