package com.rizalzaenal.jsonplaceholder.ui.screen.allpost.state

import com.rizalzaenal.jsonplaceholder.ui.screen.allpost.state.PostItemUiState

data class PostUiState(
    val isLoading: Boolean = false,
    val posts: List<PostItemUiState> = listOf(),
    val errorMessage: String = ""
)