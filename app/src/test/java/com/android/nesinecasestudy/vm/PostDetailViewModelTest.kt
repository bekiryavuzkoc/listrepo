package com.android.nesinecasestudy.vm

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.android.nesinecasestudy.ui.postdetailscreen.PostDetailScreenEvent
import com.android.nesinecasestudy.ui.postdetailscreen.PostDetailScreenIntent
import com.android.nesinecasestudy.ui.postdetailscreen.vm.PostDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class PostDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: PostDetailViewModel

    @Before
    fun setup() {
        viewModel = PostDetailViewModel()
    }

    @Test
    fun `InitialTitleAndText should update both title and body`() = runTest {

        val title = TextFieldValue("Title")
        val body = TextFieldValue("Body")

        viewModel.onIntent(
            PostDetailScreenIntent.InitialTitleAndText(title, body)
        )

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals(title, state.titleText)
        assertEquals(body, state.bodyText)
    }

    @Test
    fun `TitleTextChanged should update only title`() = runTest {

        val newTitle = TextFieldValue("New Title")

        viewModel.onIntent(
            PostDetailScreenIntent.TitleTextChanged(newTitle)
        )

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals(newTitle, state.titleText)
    }

    @Test
    fun `BodyTextChanged should update only body`() = runTest {

        val newBody = TextFieldValue("New Body")

        viewModel.onIntent(
            PostDetailScreenIntent.BodyTextChanged(newBody)
        )

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals(newBody, state.bodyText)
    }

    @Test
    fun `NavigateBackClicked should emit navigation event`() = runTest {

        viewModel.event.test {

            viewModel.onIntent(PostDetailScreenIntent.NavigateBackClicked)

            val event = awaitItem()

            assertEquals(PostDetailScreenEvent.NavigateBack, event)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
