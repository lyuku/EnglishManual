package tech.lyuku.englishmanual.core.base

sealed class DataResult<out T> {
    data class Success<out T>(val data: T?) : DataResult<T>()
    data class Error(val error: Throwable) : DataResult<Nothing>()
}