package com.rizalzaenal.jsonplaceholder.data.repository

import com.rizalzaenal.jsonplaceholder.data.model.Comment

interface PostRepository {
    suspend fun getPostComments(postId: Int): List<Comment>
}