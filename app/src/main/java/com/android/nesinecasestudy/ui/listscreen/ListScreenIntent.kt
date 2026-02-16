package com.android.nesinecasestudy.ui.listscreen

sealed interface ListScreenIntent {
    data object PullRefresh : ListScreenIntent
    data object NavigateBack : ListScreenIntent
    data class PostClicked(val title: String, val detail: String) : ListScreenIntent
    data class PostDeleted(val id: Int) : ListScreenIntent
}