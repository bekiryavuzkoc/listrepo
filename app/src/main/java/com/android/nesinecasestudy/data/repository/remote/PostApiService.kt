package com.android.nesinecasestudy.data.repository.remote

import com.android.nesinecasestudy.data.model.PostResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface PostApiService {

    @GET("posts")
    suspend fun getPostList(): Response<List<PostResponseItem>>
}