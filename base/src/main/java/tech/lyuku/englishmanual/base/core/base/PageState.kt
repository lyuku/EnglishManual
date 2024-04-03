package tech.lyuku.englishmanual.base.core.base

sealed class PageState {
    data object Loading : PageState()
    data object Loaded : PageState()
    data class Error(val message: String) : PageState()
    data object Empty : PageState()
}