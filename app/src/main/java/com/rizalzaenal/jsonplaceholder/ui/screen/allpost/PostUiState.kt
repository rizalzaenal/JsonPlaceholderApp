package com.rizalzaenal.jsonplaceholder.ui.screen.allpost

data class PostUiState(
    val isLoading: Boolean = false,
    val posts: List<PostItemUiState> = listOf(),
    val errorMessage: String = ""
)