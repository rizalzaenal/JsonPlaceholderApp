package com.rizalzaenal.jsonplaceholder.ui.screen.allpost

import com.rizalzaenal.jsonplaceholder.data.model.Post
import com.rizalzaenal.jsonplaceholder.data.model.User
import com.rizalzaenal.jsonplaceholder.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AllPostViewModelTest {
    private lateinit var viewModel: AllPostViewModel
    private lateinit var repository: UserRepository
    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = TestCoroutineDispatcher()

    private val posts = listOf<Post>(
        Post("", 1, "", 0),
        Post("", 2, "", 0),
        Post("", 3, "", 0),
    )

    private val userWithPostMap = mapOf<User, List<Post>>(
        Pair(User(null, null, "", 1, "", "", "", ""), posts),
        Pair(User(null, null, "", 2, "", "", "", ""), posts),
        Pair(User(null, null, "", 3, "", "", "", ""), posts),
    )

    private val numberOfPost = userWithPostMap.size * posts.size

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = mock()
        viewModel = AllPostViewModel(repository)

        runBlocking {
            whenever(repository.getUserWithPost()).thenReturn(userWithPostMap)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `verify number of post`() {
        viewModel.loadPosts()

        assertEquals(numberOfPost, viewModel.uiState.value.posts.size)
    }

    @Test
    fun `verify number of post empty`() {
        runBlocking {
            whenever(repository.getUserWithPost()).thenReturn(mapOf())
        }
        viewModel.loadPosts()

        assertEquals(0, viewModel.uiState.value.posts.size)
    }
}