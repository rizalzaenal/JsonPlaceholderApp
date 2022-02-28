package com.rizalzaenal.jsonplaceholder.ui.screen.postdetail

import com.rizalzaenal.jsonplaceholder.data.model.Comment
import com.rizalzaenal.jsonplaceholder.data.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PostDetailViewModelTest {
    private lateinit var viewModel: PostDetailViewModel
    private lateinit var repository: PostRepository
    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = TestCoroutineDispatcher()

    private val comments = listOf<Comment>(
        Comment("", "", 0, "", 0),
        Comment("", "", 0, "", 0),
        Comment("", "", 0, "", 0)
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = mock()
        viewModel = PostDetailViewModel(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `verify comment is not empty`() {
        runBlocking {

            whenever(repository.getPostComments(any())).thenReturn(comments)

            viewModel.loadComments(any())

            assertEquals(comments.size, viewModel.commentUiState.value.comments.size)
        }

    }

    @Test
    fun `verify comment is empty when no result from repo`() {
        runBlocking {

            whenever(repository.getPostComments(any())).thenReturn(listOf())

            viewModel.loadComments(any())

            assertEquals(0, viewModel.commentUiState.value.comments.size)
        }

    }
}