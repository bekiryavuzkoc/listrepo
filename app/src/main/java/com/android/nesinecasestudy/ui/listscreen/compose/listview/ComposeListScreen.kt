package com.android.nesinecasestudy.ui.listscreen.compose.listview

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.nesinecasestudy.ui.listscreen.ListScreenEvent
import com.android.nesinecasestudy.ui.listscreen.ListScreenIntent
import com.android.nesinecasestudy.ui.listscreen.compose.postview.PostComposeView
import com.android.nesinecasestudy.ui.listscreen.vm.ListViewModel
import com.android.nesinecasestudy.ui.theme.lightGray

@Composable
fun ComposeListScreen(
    viewModel: ListViewModel = hiltViewModel(),
    onNavigateToPostDetail: (Int, String, String) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val listState = rememberLazyListState()
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                ListScreenEvent.NavigateBack -> {
                    onNavigateBack()
                }

                is ListScreenEvent.NavigateToPostDetail -> {
                    onNavigateToPostDetail(event.id, event.title, event.detail)
                }
            }
        }
    }

    when {
        uiState.isPostListLoading && uiState.postList.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.fatalError != null -> {
            uiState.fatalError?.let {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(it)
                }
            }
        }

        else -> {
            PullToRefreshBox(
                isRefreshing = uiState.isPostListRefreshing,
                onRefresh = {
                    viewModel.onIntent(ListScreenIntent.PullRefresh)
                },
                state = pullToRefreshState
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(lightGray),
                ) {
                    itemsIndexed(
                        items = uiState.postList,
                        key = { _, item -> item.id }
                    ) { index, post ->
                        PostComposeView(
                            post = post,
                            deletePost = { id ->
                                viewModel.onIntent(
                                    ListScreenIntent.PostDeleted(id)
                                )
                            },
                            onClick = { id, title, body ->
                                viewModel.onIntent(
                                    ListScreenIntent.PostClicked(id, title, body)
                                )
                            }
                        )

                        if (index != uiState.postList.lastIndex) {
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }

    BackHandler { viewModel.onIntent(ListScreenIntent.NavigateBack) }
}