package com.rizalzaenal.jsonplaceholder.ui.screen.postdetail.state

data class CommentUiState(
    val isLoading: Boolean = false,
    val comments: List<CommentItemUiState> = listOf(),
    val errorMessage: String = ""
)
