package com.android.nesinecasestudy.usecase

import com.android.nesinecasestudy.domain.model.Post
import com.android.nesinecasestudy.domain.repository.PostRepository
import com.android.nesinecasestudy.domain.usecase.GetPostsUseCase
import com.android.nesinecasestudy.domain.utils.NetworkError
import com.android.nesinecasestudy.domain.utils.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetPostsUseCaseTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var repository: PostRepository
    private lateinit var useCase: GetPostsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetPostsUseCase(
            repository = repository,
            ioDispatcher = dispatcher
        )
    }

    @Test
    fun `get should return success result from repository`() = runTest {
        val fakePosts = listOf(Post(1, 1, "Title", "Body"))
        val expectedResult = Result.Success(fakePosts)

        coEvery { repository.getPosts() } returns expectedResult

        val result = useCase.get()

        assertEquals(expectedResult, result)
        coVerify { repository.getPosts() }
    }

    @Test
    fun `get should return error result from repository`() = runTest {
        val expectedError = Result.Error(NetworkError.SERVER_ERROR)

        coEvery { repository.getPosts() } returns expectedError

        val result = useCase.get()

        assertEquals(expectedError, result)
        coVerify { repository.getPosts() }
    }
}