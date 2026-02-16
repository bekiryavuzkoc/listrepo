package com.android.nesinecasestudy.domain.usecase

import com.android.nesinecasestudy.di.IODispatcher
import com.android.nesinecasestudy.domain.model.Post
import com.android.nesinecasestudy.domain.repository.PostRepository
import com.android.nesinecasestudy.domain.utils.NetworkError
import com.android.nesinecasestudy.domain.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val repository: PostRepository,
    @IODispatcher ioDispatcher: CoroutineDispatcher,
) : BaseApiUseCase<List<Post>, Unit>(ioDispatcher) {

    override suspend fun executeOnBackground(param: Unit): Result<List<Post>, NetworkError> {
        return repository.getPost()
    }

    suspend fun get(param: Unit = Unit) = execute(param)
}