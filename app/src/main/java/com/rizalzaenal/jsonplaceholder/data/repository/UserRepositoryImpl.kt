package com.rizalzaenal.jsonplaceholder.data.repository

import com.rizalzaenal.jsonplaceholder.data.model.PostItem
import com.rizalzaenal.jsonplaceholder.data.model.UserItem
import com.rizalzaenal.jsonplaceholder.data.remote.JsonPlaceHolderService
import javax.inject.Inject

class UserRepositoryImpl (private val service: JsonPlaceHolderService) :
    UserRepository {

    override suspend fun getUsers(): List<UserItem> {
        return service.getUsers()
    }

    override suspend fun getPostByUser(userId: Int): List<PostItem> {
        return service.getPostsByUser(userId)
    }

    override suspend fun getUserWithPost(): Map<UserItem, List<PostItem>> {
        val result = mutableMapOf<UserItem, List<PostItem>>()
        val users = service.getUsers()
        users.forEach { user ->
            val posts = service.getPostsByUser(user.id ?: return@forEach)
            result[user] = posts
        }
        return result
    }
}