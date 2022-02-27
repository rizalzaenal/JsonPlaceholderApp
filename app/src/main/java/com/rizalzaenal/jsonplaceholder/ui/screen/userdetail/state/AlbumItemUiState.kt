package com.rizalzaenal.jsonplaceholder.ui.screen.userdetail.state

data class AlbumItemUiState(
    val title: String = "",
    val photos: List<PhotoItemUiState> = listOf()
)
