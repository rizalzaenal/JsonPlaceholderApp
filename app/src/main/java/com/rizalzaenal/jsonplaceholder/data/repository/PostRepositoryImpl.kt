package com.rizalzaenal.jsonplaceholder.data.repository

import com.rizalzaenal.jsonplaceholder.data.model.Comment
import com.rizalzaenal.jsonplaceholder.data.remote.JsonPlaceHolderService

class PostRepositoryImpl(private val service: JsonPlaceHolderService): PostRepository {
    override suspend fun getPostComments(postId: Int): List<Comment> {
        return service.getPostComments(postId)
    }
}