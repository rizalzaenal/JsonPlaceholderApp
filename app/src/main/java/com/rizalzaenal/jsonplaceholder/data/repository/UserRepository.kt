package com.rizalzaenal.jsonplaceholder.data.repository

import com.rizalzaenal.jsonplaceholder.data.model.Post
import com.rizalzaenal.jsonplaceholder.data.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUserDetail(userId: Int): User
    suspend fun getPostByUser(userId: Int): List<Post>
    suspend fun getUserWithPost(): Map<User, List<Post>>
}