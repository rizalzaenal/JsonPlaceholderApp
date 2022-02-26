package com.rizalzaenal.jsonplaceholder.data.remote

import com.rizalzaenal.jsonplaceholder.data.model.Comment
import com.rizalzaenal.jsonplaceholder.data.model.Post
import com.rizalzaenal.jsonplaceholder.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceHolderService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{userId}/posts")
    suspend fun getPostsByUser(@Path("userId") userId: Int): List<Post>

    @GET("posts/{postId}/comments")
    suspend fun getPostComments(@Path("postId") postId: Int): List<Comment>
}