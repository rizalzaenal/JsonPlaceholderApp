package com.rizalzaenal.jsonplaceholder.data.repository

import com.rizalzaenal.jsonplaceholder.data.model.Album
import com.rizalzaenal.jsonplaceholder.data.model.Photo
import com.rizalzaenal.jsonplaceholder.data.remote.JsonPlaceHolderService

class AlbumRepositoryImpl(private val service: JsonPlaceHolderService) : AlbumRepository {
    override suspend fun getPhotos(albumId: Int): List<Photo> {
        return service.getPhotos(albumId)
    }

    override suspend fun getAlbumsWithPhotos(userId: Int): Map<Album, List<Photo>> {
        val result = mutableMapOf<Album, List<Photo>>()
        val userAlbums = service.getUserAlbums(userId)
        userAlbums.forEach { album ->
            val photos = service.getPhotos(album.id ?: return@forEach)
            result[album] = photos
        }
        return result
    }
}