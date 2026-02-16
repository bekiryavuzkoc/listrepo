package com.android.nesinecasestudy.ui.listscreen

sealed interface ListScreenEvent {
    object NavigateBack : ListScreenEvent
    data class NavigateToPostDetail(val id: Int, val title: String, val detail: String) : ListScreenEvent
}