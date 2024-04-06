package tech.lyuku.englishmanual.base.core.base

import android.util.Log
import android.view.View
import android.view.ViewGroup
import tech.lyuku.englishmanual.base.core.ui.EmptyView
import tech.lyuku.englishmanual.base.core.ui.ErrorView
import tech.lyuku.englishmanual.base.core.ui.LoadingView

abstract class AEMBasePageStateActivity : EMBaseActivity() {

    private lateinit var emptyView: EmptyView
    private lateinit var errorView: ErrorView
    private lateinit var loadingProgressBar: LoadingView

    open fun getInfoContainer(): ViewGroup? {
        return null
    }

    fun showEmptyView() {
        if (!this::emptyView.isInitialized) {
            emptyView = EmptyView(this)
        }
        containerShowView(emptyView)
        emptyView.showEmpty()
    }

    fun showErrorView(message: String, onRetry: () -> Unit) {
        if (!this::errorView.isInitialized) {
            errorView = ErrorView(this)

        }
        containerShowView(errorView)
        errorView.setOnRetry {
            onRetry.invoke()
        }
        errorView.showError(message)
    }

    fun showLoadingView() {
        if (!this::loadingProgressBar.isInitialized) {
            loadingProgressBar = LoadingView(this)
        }
        containerShowView(loadingProgressBar)
    }

    private fun containerShowView(view: View) {
        if (getInfoContainer() == null) {
            Log.e("BaseActivity", "you should override getInfoContainer()")
            return
        }
        getInfoContainer()?.removeAllViews()
        getInfoContainer()?.addView(view)
    }

    fun hideInfoViews() {
        if (getInfoContainer() == null) {
            Log.e("BaseActivity", "you should override getInfoContainer()")
            return
        }
        getInfoContainer()?.removeAllViews()
        getInfoContainer()?.visibility = View.GONE
    }

}