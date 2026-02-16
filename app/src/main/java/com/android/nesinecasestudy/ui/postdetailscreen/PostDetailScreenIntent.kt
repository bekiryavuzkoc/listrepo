package com.android.nesinecasestudy.ui.postdetailscreen

import androidx.compose.ui.text.input.TextFieldValue

sealed interface PostDetailScreenIntent {
    data class InitialTitleAndText(val titleText: TextFieldValue, val bodyText: TextFieldValue) : PostDetailScreenIntent
    data class TitleTextChanged(val titleText: TextFieldValue) : PostDetailScreenIntent
    data class BodyTextChanged(val bodyText: TextFieldValue) : PostDetailScreenIntent
    data object NavigateBackClicked : PostDetailScreenIntent
}
