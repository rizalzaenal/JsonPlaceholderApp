package com.rizalzaenal.jsonplaceholder.ui.screen.userdetail.state

data class AlbumUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val albums: List<AlbumItemUiState> = listOf(),
)
