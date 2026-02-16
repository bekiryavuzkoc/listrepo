package com.android.nesinecasestudy.ui.postdetailscreen

import androidx.compose.ui.text.input.TextFieldValue
import com.android.nesinecasestudy.domain.utils.emptyString

data class PostDetailUIState(
    val titleText: TextFieldValue = TextFieldValue(String().emptyString()),
    val bodyText: TextFieldValue = TextFieldValue(String().emptyString()),
)