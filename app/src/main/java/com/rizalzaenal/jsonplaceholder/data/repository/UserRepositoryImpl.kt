package com.rizalzaenal.jsonplaceholder.data.repository

import com.rizalzaenal.jsonplaceholder.data.model.Comment
import com.rizalzaenal.jsonplaceholder.data.model.Post
import com.rizalzaenal.jsonplaceholder.data.model.User
import com.rizalzaenal.jsonplaceholder.data.remote.JsonPlaceHolderService

class UserRepositoryImpl (private val service: JsonPlaceHolderService) :
    UserRepository {

    override suspend fun getUsers(): List<User> {
        return service.getUsers()
    }

    override suspend fun getUserDetail(userId: Int): User {
        return service.getUserDetail(userId)
    }

    override suspend fun getPostByUser(userId: Int): List<Post> {
        return service.getPostsByUser(userId)
    }

    override suspend fun getUserWithPost(): Map<User, List<Post>> {
        val result = mutableMapOf<User, List<Post>>()
        val users = service.getUsers()
        users.forEach { user ->
            val posts = service.getPostsByUser(user.id ?: return@forEach)
            result[user] = posts
        }
        return result
    }
}