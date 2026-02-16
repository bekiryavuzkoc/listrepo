package com.android.nesinecasestudy.domain.repository

import com.android.nesinecasestudy.data.model.PostResponseItem
import com.android.nesinecasestudy.domain.utils.NetworkError
import com.android.nesinecasestudy.domain.utils.Result

interface PostRemoteDataSource {

    suspend fun getPostList(): Result<List<PostResponseItem>, NetworkError>
}