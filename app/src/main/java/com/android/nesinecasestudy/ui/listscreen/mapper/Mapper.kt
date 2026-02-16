package com.android.nesinecasestudy.ui.listscreen.mapper

import com.android.nesinecasestudy.domain.model.Post
import com.android.nesinecasestudy.ui.listscreen.model.PostItemUiModel

fun Post.toUIModel(): PostItemUiModel {
    return PostItemUiModel(
        id = id,
        title = title,
        body = body,
    )
}
