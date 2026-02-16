package com.android.nesinecasestudy.ui.postdetailscreen

sealed interface PostDetailScreenEvent {
    object NavigateBack : PostDetailScreenEvent
}