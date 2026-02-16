package com.android.nesinecasestudy.ui.postdetailscreen.vm

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.nesinecasestudy.ui.postdetailscreen.PostDetailScreenEvent
import com.android.nesinecasestudy.ui.postdetailscreen.PostDetailScreenIntent
import com.android.nesinecasestudy.ui.postdetailscreen.PostDetailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PostDetailUIState())
    val uiState: StateFlow<PostDetailUIState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<PostDetailScreenEvent>()
    val event: SharedFlow<PostDetailScreenEvent> = _event.asSharedFlow()

    fun onIntent(intent: PostDetailScreenIntent) {
        when (intent) {
            PostDetailScreenIntent.NavigateBackClicked -> emitEvent(PostDetailScreenEvent.NavigateBack)
            is PostDetailScreenIntent.InitialTitleAndText -> setInitialTitleAndBodyText(
                intent.titleText,
                intent.bodyText
            )

            is PostDetailScreenIntent.TitleTextChanged -> {
                setTitleText(intent.titleText)
            }

            is PostDetailScreenIntent.BodyTextChanged -> {
               setBodyText(intent.bodyText)
            }
        }
    }

    private fun setInitialTitleAndBodyText(title: TextFieldValue, body: TextFieldValue) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    titleText = title,
                    bodyText = body
                )
            }
        }
    }

    private fun setTitleText(text: TextFieldValue) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    titleText = text,
                )
            }
        }
    }

    private fun setBodyText(text: TextFieldValue) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    bodyText = text
                )
            }
        }
    }

    private fun emitEvent(event: PostDetailScreenEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }
}
