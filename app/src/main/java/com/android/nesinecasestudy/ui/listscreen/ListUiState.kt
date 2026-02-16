package com.android.nesinecasestudy.ui.listscreen

import com.android.nesinecasestudy.ui.listscreen.model.PostItemUiModel

data class ListUiState(
    val postList: List<PostItemUiModel> = emptyList(),
    val isPostListLoading: Boolean = false,
    val isPostListRefreshing: Boolean = false,
    val fatalError: String? = null
)