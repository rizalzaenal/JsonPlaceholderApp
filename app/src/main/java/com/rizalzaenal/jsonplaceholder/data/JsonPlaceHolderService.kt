package com.rizalzaenal.jsonplaceholder.data

import com.rizalzaenal.jsonplaceholder.data.model.PostItem
import com.rizalzaenal.jsonplaceholder.data.model.UserItem
import retrofit2.http.GET

interface JsonPlaceHolderService {

    @GET("posts")
    suspend fun getPosts(): List<PostItem>

    @GET("users")
    suspend fun getUsers(): List<UserItem>
}