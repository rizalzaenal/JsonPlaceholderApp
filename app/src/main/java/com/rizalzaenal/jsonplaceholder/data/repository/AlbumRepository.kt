package com.rizalzaenal.jsonplaceholder.data.repository

import com.rizalzaenal.jsonplaceholder.data.model.Album
import com.rizalzaenal.jsonplaceholder.data.model.Photo


interface AlbumRepository {
    suspend fun getPhotos(albumId: Int): List<Photo>
    suspend fun getAlbumsWithPhotos(userId: Int): Map<Album, List<Photo>>
}