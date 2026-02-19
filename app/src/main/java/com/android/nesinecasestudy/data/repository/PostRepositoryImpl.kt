package com.android.nesinecasestudy.data.repository

import com.android.nesinecasestudy.data.mapper.toDomain
import com.android.nesinecasestudy.data.repository.remote.PostRemoteDataSource
import com.android.nesinecasestudy.domain.model.Post
import com.android.nesinecasestudy.domain.repository.PostRepository
import com.android.nesinecasestudy.domain.utils.NetworkError
import com.android.nesinecasestudy.domain.utils.Result
import com.android.nesinecasestudy.domain.utils.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
    private val remoteDataSource: PostRemoteDataSource,
) : PostRepository {

    override suspend fun getPosts(): Result<List<Post>, NetworkError> {
        return remoteDataSource.getPostList()
            .map { it.toDomain() }
    }
}