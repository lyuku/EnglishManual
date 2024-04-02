package tech.lyuku.englishmanual.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import tech.lyuku.englishmanual.databinding.ViewErrorBinding

class ErrorView(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attributeSet, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    private var viewBinding: ViewErrorBinding

    init {
        viewBinding = ViewErrorBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun showError(message: String) {
        viewBinding.tvErrorMessage.text = message
        visibility = VISIBLE
    }

    fun hideError() {
        visibility = GONE
    }

    fun setOnRetry(listener: () -> Unit) {
        viewBinding.btRetry.setOnClickListener {
            listener.invoke()
        }
    }
}