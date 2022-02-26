package com.rizalzaenal.jsonplaceholder.data.repository

import com.rizalzaenal.jsonplaceholder.data.model.PostItem
import com.rizalzaenal.jsonplaceholder.data.model.UserItem

interface UserRepository {
    suspend fun getUsers(): List<UserItem>
    suspend fun getPostByUser(userId: Int): List<PostItem>
    suspend fun getUserWithPost(): Map<UserItem, List<PostItem>>
}