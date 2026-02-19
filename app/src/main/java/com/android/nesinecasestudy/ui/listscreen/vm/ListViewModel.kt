package com.android.nesinecasestudy.ui.listscreen.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.nesinecasestudy.domain.usecase.GetPostsUseCase
import com.android.nesinecasestudy.domain.utils.Result
import com.android.nesinecasestudy.ui.listscreen.ListScreenEvent
import com.android.nesinecasestudy.ui.listscreen.ListScreenIntent
import com.android.nesinecasestudy.ui.listscreen.ListUiState
import com.android.nesinecasestudy.ui.listscreen.mapper.toUIModel
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
class ListViewModel @Inject constructor(
    private val getPostUseCase: GetPostsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ListScreenEvent>()
    val event: SharedFlow<ListScreenEvent> = _event.asSharedFlow()

    init {
        loadInitial()
    }

    fun onIntent(intent: ListScreenIntent) {
        when (intent) {
            ListScreenIntent.PullRefresh -> pullRefreshPostList()
            ListScreenIntent.NavigateBack -> emitEvent(ListScreenEvent.NavigateBack)
            is ListScreenIntent.PostClicked -> {
                emitEvent(ListScreenEvent.NavigateToPostDetail(intent.title, intent.detail))
            }

            is ListScreenIntent.PostDeleted -> {
                deleteItem(intent.id)
            }
        }
    }

    private fun loadInitial() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isPostListLoading = true,
                    fatalError = null
                )
            }

            getPostList()
        }
    }

    private fun pullRefreshPostList() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isPostListRefreshing = true,
                    fatalError = null,
                )
            }

            getPostList()
        }
    }

    private suspend fun getPostList() {
        when (val result = getPostUseCase.get()) {
            is Result.Success -> {
                _uiState.update {
                    it.copy(
                        isPostListLoading = false,
                        isPostListRefreshing = false,
                        postList = result.data.map { post ->
                            post.toUIModel()
                        },
                    )
                }
            }

            is Result.Error -> {
                _uiState.update {
                    it.copy(
                        isPostListLoading = false,
                        isPostListRefreshing = false,
                        fatalError = "Something went wrong"
                    )
                }
            }
        }
    }

    private fun deleteItem(id: Int) {
        _uiState.update {
            it.copy(
                postList = it.postList.filterNot { post ->
                    post.id == id
                }
            )
        }
    }

    private fun emitEvent(event: ListScreenEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }
}
