package com.rizalzaenal.jsonplaceholder.ui.screen.userdetail

import com.rizalzaenal.jsonplaceholder.data.model.Album
import com.rizalzaenal.jsonplaceholder.data.model.Photo
import com.rizalzaenal.jsonplaceholder.data.model.User
import com.rizalzaenal.jsonplaceholder.data.repository.AlbumRepository
import com.rizalzaenal.jsonplaceholder.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class UserDetailViewModelTest {
    private lateinit var viewModel: UserDetailViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var albumRepository: AlbumRepository
    private val dispatcher = TestCoroutineDispatcher()

    private val photos = listOf<Photo>(
        Photo(0, 0, "", "", ""),
        Photo(0, 0, "", "", ""),
        Photo(0, 0, "", "", ""),
    )

    private val albumsMap = mapOf<Album, List<Photo>>(
        Pair(Album(1, "", 0), photos),
        Pair(Album(2, "", 0), photos),
        Pair(Album(3, "", 0), photos),
    )

    private val user = User(null, null, "", 0, "", "", "", "")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        userRepository = mock()
        albumRepository = mock()
        viewModel = UserDetailViewModel(userRepository, albumRepository)

        runBlocking {
            whenever(userRepository.getUserDetail(any())).thenReturn(user)
            whenever(albumRepository.getAlbumsWithPhotos(any())).thenReturn(albumsMap)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `verify list value not empty`() {
        viewModel.getUserAlbumsWithPhotos(any())

        Assert.assertEquals(albumsMap.size, viewModel.albumState.value.albums.size)

        viewModel.albumState.value.albums.forEach {
            Assert.assertEquals(photos.size, it.photos.size)
        }
    }

    @Test
    fun `verify list value empty when repo return empty`() {
        runBlocking {
            whenever(albumRepository.getAlbumsWithPhotos(any())).thenReturn(mapOf())
        }
        viewModel.getUserAlbumsWithPhotos(any())

        Assert.assertEquals(0, viewModel.albumState.value.albums.size)

        viewModel.albumState.value.albums.forEach {
            Assert.assertEquals(0, it.photos.size)
        }
    }
}