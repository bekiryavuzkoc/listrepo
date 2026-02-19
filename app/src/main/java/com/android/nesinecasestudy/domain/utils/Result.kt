package com.android.nesinecasestudy.domain.utils

sealed interface Result<out D, out E: NetworkError> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: NetworkError>(val error: E): Result<Nothing, E>
}

inline fun <T, E: NetworkError, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}