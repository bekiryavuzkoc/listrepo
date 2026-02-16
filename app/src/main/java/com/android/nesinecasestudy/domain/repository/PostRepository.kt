package com.android.nesinecasestudy.domain.repository

import com.android.nesinecasestudy.domain.model.Post
import com.android.nesinecasestudy.domain.utils.NetworkError
import com.android.nesinecasestudy.domain.utils.Result

interface PostRepository {

    suspend fun getPost(): Result<List<Post>, NetworkError>
}