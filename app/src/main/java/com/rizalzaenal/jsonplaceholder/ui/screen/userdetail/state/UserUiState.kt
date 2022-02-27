package com.rizalzaenal.jsonplaceholder.ui.screen.userdetail.state

data class UserUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val user: UserItemUiState = UserItemUiState(),
)