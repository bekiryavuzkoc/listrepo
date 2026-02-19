package com.android.nesinecasestudy.repository

import com.android.nesinecasestudy.data.model.PostResponseItem
import com.android.nesinecasestudy.data.repository.PostRepositoryImpl
import com.android.nesinecasestudy.data.repository.remote.PostRemoteDataSource
import com.android.nesinecasestudy.domain.model.Post
import com.android.nesinecasestudy.domain.utils.NetworkError
import com.android.nesinecasestudy.domain.utils.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class PostRepositoryImplTest {

    private lateinit var remoteDataSource: PostRemoteDataSource
    private lateinit var repository: PostRepositoryImpl

    @Before
    fun setup() {
        remoteDataSource = mockk()
        repository = PostRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `getPosts should return mapped posts when remote is success`() = runTest {
        // Given
        val responseItems = listOf(
            PostResponseItem(
                userId = 1,
                id = 1,
                title = "Title",
                body = "Body"
            )
        )

        coEvery { remoteDataSource.getPostList() } returns
                Result.Success(responseItems)

        // When
        val result = repository.getPosts()

        // Then
        coVerify { remoteDataSource.getPostList() }

        assertTrue(result is Result.Success)
        assertEquals(1, result.data.size)
        assertEquals(
            Post(
                userId = 1,
                id = 1,
                title = "Title",
                body = "Body"
            ),
            result.data.first()
        )
    }

    @Test
    fun `getPosts should return error when remote fails`() = runTest {
        val expectedError = Result.Error(NetworkError.SERVER_ERROR)

        coEvery { remoteDataSource.getPostList() } returns expectedError

        val result = repository.getPosts()
        coVerify { remoteDataSource.getPostList() }
        assertEquals(expectedError, result)
    }
}