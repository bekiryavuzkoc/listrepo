package com.android.nesinecasestudy.vm

import app.cash.turbine.test
import com.android.nesinecasestudy.domain.model.Post
import com.android.nesinecasestudy.domain.usecase.GetPostsUseCase
import com.android.nesinecasestudy.domain.utils.NetworkError
import com.android.nesinecasestudy.domain.utils.Result
import com.android.nesinecasestudy.ui.listscreen.ListScreenEvent
import com.android.nesinecasestudy.ui.listscreen.ListScreenIntent
import com.android.nesinecasestudy.ui.listscreen.vm.ListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var useCase: GetPostsUseCase
    private lateinit var viewModel: ListViewModel

    @Before
    fun setup() {
        useCase = mockk()
    }

    @Test
    fun `init should load posts successfully`() = runTest {
        val posts = listOf(
            Post(1, 1, "Title", "Body")
        )

        coEvery { useCase.get() } returns Result.Success(posts)

        viewModel = ListViewModel(useCase)

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertFalse(state.isPostListLoading)
        assertEquals(1, state.postList.size)
        assertNull(state.fatalError)
    }

    @Test
    fun `init should handle error`() = runTest {
        coEvery { useCase.get() } returns Result.Error(NetworkError.SERVER_ERROR)

        viewModel = ListViewModel(useCase)

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertFalse(state.isPostListLoading)
        assertEquals("Something went wrong", state.fatalError)
    }

    @Test
    fun `pull refresh should update refreshing state`() = runTest {
        val posts = listOf(Post(1, 1, "Title", "Body"))
        coEvery { useCase.get() } returns Result.Success(posts)

        viewModel = ListViewModel(useCase)

        advanceUntilIdle()

        viewModel.onIntent(ListScreenIntent.PullRefresh)

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertFalse(state.isPostListRefreshing)
        assertEquals(1, state.postList.size)
    }

    @Test
    fun `delete intent should remove item from list`() = runTest {
        val posts = listOf(
            Post(1, 1, "Title", "Body"),
            Post(1, 2, "Title2", "Body2")
        )

        coEvery { useCase.get() } returns Result.Success(posts)

        viewModel = ListViewModel(useCase)

        advanceUntilIdle()

        viewModel.onIntent(ListScreenIntent.PostDeleted(1))

        val state = viewModel.uiState.value

        assertEquals(1, state.postList.size)
        assertEquals(2, state.postList.first().id)
    }

    @Test
    fun `post clicked should emit navigation event`() = runTest {
        coEvery { useCase.get() } returns Result.Success(emptyList())

        viewModel = ListViewModel(useCase)

        advanceUntilIdle()

        viewModel.event.test {
            viewModel.onIntent(
                ListScreenIntent.PostClicked("Title", "Detail")
            )

            val event = awaitItem()

            assertTrue(event is ListScreenEvent.NavigateToPostDetail)
            cancelAndIgnoreRemainingEvents()
        }
    }
}