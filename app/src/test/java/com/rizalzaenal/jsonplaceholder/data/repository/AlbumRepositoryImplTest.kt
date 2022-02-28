package com.rizalzaenal.jsonplaceholder.data.repository

import com.rizalzaenal.jsonplaceholder.data.model.Album
import com.rizalzaenal.jsonplaceholder.data.model.Photo
import com.rizalzaenal.jsonplaceholder.data.remote.JsonPlaceHolderService
import kotlinx.coroutines.runBlocking
import org.junit.Assert

import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class AlbumRepositoryImplTest {
    private lateinit var repository: AlbumRepositoryImpl
    private lateinit var service: JsonPlaceHolderService

    private val photos = listOf<Photo>(
        Photo(0, 1, "", "", ""),
        Photo(0, 2, "", "", ""),
        Photo(0, 3, "", "", "")
    )

    private val albums = listOf<Album>(
        Album(1, "", 0),
        Album(2, "", 0),
        Album(3, "", 0)
    )

    @Before
    fun setUp() {
        service = mock()
        repository = AlbumRepositoryImpl(service)

        runBlocking {
            whenever(service.getPhotos(any())).thenReturn(photos)
            whenever(service.getUserAlbums(any())).thenReturn(albums)
        }
    }

    @Test
    fun `verify service called`() {
        runBlocking {
            repository.getAlbumsWithPhotos(any())
            verify(service).getUserAlbums(any())
            verify(service, times(albums.size)).getPhotos(any())
        }
    }

    @Test
    fun `verify returned map size is correct`() {
        runBlocking {
            val returnedMap = repository.getAlbumsWithPhotos(any())

            Assert.assertEquals(albums.size, returnedMap.size)
        }
    }

    @Test
    fun `verify photos per albums size is correct`() {
        runBlocking {
            val returnedMap = repository.getAlbumsWithPhotos(any())

            returnedMap.forEach {
                Assert.assertEquals(photos.size, it.value.size)
            }
        }
    }
}