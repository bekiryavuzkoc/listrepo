package com.android.nesinecasestudy.data.repository.remote

import com.android.nesinecasestudy.data.model.PostResponseItem
import com.android.nesinecasestudy.domain.utils.NetworkError
import com.android.nesinecasestudy.domain.utils.Result
import com.android.nesinecasestudy.domain.utils.handleApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRemoteDataSourceImpl @Inject constructor(
    private val apiService: PostApiService
) : PostRemoteDataSource {

    override suspend fun getPostList(): Result<List<PostResponseItem>, NetworkError> {
        return handleApi {
            apiService.getPostList()
        }
    }
}