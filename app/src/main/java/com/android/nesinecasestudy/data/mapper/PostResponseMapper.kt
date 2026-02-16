package com.android.nesinecasestudy.data.mapper

import com.android.nesinecasestudy.data.model.PostResponseItem
import com.android.nesinecasestudy.domain.model.Post

fun List<PostResponseItem>?.toDomain(): List<Post> {
    return this?.map { item ->
        Post(
            userId = item.userId ?: 0,
            id = item.id ?: 0,
            title = item.title.orEmpty(),
            body = item.body.orEmpty()
        )
    } ?: emptyList()
}