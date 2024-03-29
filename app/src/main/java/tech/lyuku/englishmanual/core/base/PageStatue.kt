package tech.lyuku.englishmanual.core.base

sealed class PageStatue {
    data object Loading : PageStatue()
    data object Loaded : PageStatue()
    data class Error(val message: String) : PageStatue()
    data object Empty : PageStatue()
}