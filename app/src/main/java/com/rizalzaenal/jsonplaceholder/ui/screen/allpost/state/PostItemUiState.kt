package com.rizalzaenal.jsonplaceholder.ui.screen.allpost.state

data class PostItemUiState(
    val postId: Int = 0,
    val name: String = "",
    val username: String = "",
    val companyName: String = "",
    val postTitle: String = "",
    val postBody: String = ""
)