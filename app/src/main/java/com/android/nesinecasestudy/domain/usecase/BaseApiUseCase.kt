package com.android.nesinecasestudy.domain.usecase

import com.android.nesinecasestudy.domain.utils.Result
import com.android.nesinecasestudy.domain.utils.NetworkError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseApiUseCase<Model, Param>(
    private val ioDispatcher: CoroutineDispatcher
) {

    protected abstract suspend fun executeOnBackground(param: Param): Result<Model, NetworkError>

    suspend fun execute(param: Param): Result<Model, NetworkError> {
        return withContext(ioDispatcher) {
            executeOnBackground(param)
        }
    }
}