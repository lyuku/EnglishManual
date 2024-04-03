package tech.lyuku.englishmanual.base.core.base

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import tech.lyuku.englishmanual.base.core.ui.EmptyView
import tech.lyuku.englishmanual.base.core.ui.ErrorView
import tech.lyuku.englishmanual.base.core.ui.LoadingView

abstract class AEMBasePageStateActivity : EMBaseActivity() {

    private lateinit var emptyView: EmptyView
    private lateinit var errorView: ErrorView
    private lateinit var loadingProgressBar: LoadingView

    private lateinit var container: ViewGroup
    fun initPageState(
        pageState: LiveData<PageState>,
        container: ViewGroup,
        onRetry: () -> Unit
    ) {
        this.container = container
        pageState.observe(this) {
            when (it) {
                is PageState.Error -> {
                    showErrorView(it.message, onRetry)
                }

                is PageState.Loading -> {
                    showLoadingView()
                }

                is PageState.Loaded -> {
                    hideInfoViews()
                }

                is PageState.Empty -> {
                    showEmptyView()
                }
            }
        }
    }

    private fun showEmptyView() {
        if (!this::emptyView.isInitialized) {
            emptyView = EmptyView(this)
        }
        containerShowView(emptyView)
        emptyView.showEmpty()
    }

    private fun showErrorView(message: String, onRetry: () -> Unit) {
        if (!this::errorView.isInitialized) {
            errorView = ErrorView(this)

        }
        containerShowView(errorView)
        errorView.setOnRetry {
            onRetry.invoke()
        }
        errorView.showError(message)
    }

    private fun showLoadingView() {
        if (!this::loadingProgressBar.isInitialized) {
            loadingProgressBar = LoadingView(this)
        }
        containerShowView(loadingProgressBar)
    }

    private fun containerShowView(view: View) {
        this.container.removeAllViews()
        this.container.addView(view)
    }

    private fun hideInfoViews() {
        this.container.removeAllViews()
        this.container.visibility = View.GONE
    }

}