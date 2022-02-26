package com.rizalzaenal.jsonplaceholder.data.remote

import com.rizalzaenal.jsonplaceholder.data.model.PostItem
import com.rizalzaenal.jsonplaceholder.data.model.UserItem
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceHolderService {

    @GET("users")
    suspend fun getUsers(): List<UserItem>

    @GET("users/{userId}/posts")
    suspend fun getPostsByUser(@Path("userId") userId: Int): List<PostItem>
}