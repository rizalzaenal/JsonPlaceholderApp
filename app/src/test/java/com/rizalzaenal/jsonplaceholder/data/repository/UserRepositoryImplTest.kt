package com.rizalzaenal.jsonplaceholder.data.repository

import com.rizalzaenal.jsonplaceholder.data.model.Post
import com.rizalzaenal.jsonplaceholder.data.model.User
import com.rizalzaenal.jsonplaceholder.data.remote.JsonPlaceHolderService
import kotlinx.coroutines.runBlocking
import org.junit.Assert

import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UserRepositoryImplTest {
    private lateinit var repository: UserRepositoryImpl
    private lateinit var service: JsonPlaceHolderService

    private val users = listOf<User>(
        User(null, null, "", 0, "user1", "", "", ""),
        User(null, null, "", 0, "user2", "", "", ""),
        User(null, null, "", 0, "user3", "", "", "")
    )

    private val userPost = listOf<Post>(
        Post("",0, "", 0),
        Post("",0, "", 0),
        Post("",0, "", 0)
    )

    @Before
    fun setUp() {
        service = mock()
        repository = UserRepositoryImpl(service)
        runBlocking {
            whenever(service.getUsers()).thenReturn(users)
            whenever(service.getPostsByUser(any())).thenReturn(userPost)
        }
    }

    @Test
    fun `verify service getUsers method called`() {
        runBlocking {
            repository.getUserWithPost()
            verify(service).getUsers()
        }
    }

    @Test
    fun `verify getUserWithPost returned map size is correct`() {
        runBlocking {
            val returnedMap = repository.getUserWithPost()

            Assert.assertEquals(users.size, returnedMap.size)
        }
    }

    @Test
    fun `verify getUserWithPost post per user is correct`() {
        runBlocking {
            val returnedMap = repository.getUserWithPost()

            returnedMap.forEach {
                Assert.assertEquals(userPost.size, it.value.size)
            }
        }
    }
}