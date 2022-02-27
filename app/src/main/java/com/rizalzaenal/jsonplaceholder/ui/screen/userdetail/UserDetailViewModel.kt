package com.rizalzaenal.jsonplaceholder.ui.screen.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizalzaenal.jsonplaceholder.data.model.Album
import com.rizalzaenal.jsonplaceholder.data.model.Photo
import com.rizalzaenal.jsonplaceholder.data.replaceNull
import com.rizalzaenal.jsonplaceholder.data.repository.AlbumRepository
import com.rizalzaenal.jsonplaceholder.data.repository.UserRepository
import com.rizalzaenal.jsonplaceholder.ui.screen.userdetail.state.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val albumRepository: AlbumRepository
) :
    ViewModel() {
    private val _userState = MutableStateFlow<UserUiState>(UserUiState())
    val userState: StateFlow<UserUiState> = _userState

    private val _albumState = MutableStateFlow(AlbumUiState())
    val albumState: StateFlow<AlbumUiState> = _albumState

    fun getUser(userId: Int) {
        _userState.value = UserUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val data = userRepository.getUserDetail(userId)
                val userItemUiState = UserItemUiState(
                    name = data.name.replaceNull(),
                    userName = data.username.replaceNull(),
                    email = data.email.replaceNull(),
                    company = data.company?.name.replaceNull(),
                    address = "${data.address?.suite}, ${data.address?.street}, " +
                            "${data.address?.city}, ${data.address?.zipcode}"
                )
                _userState.value = UserUiState(user = userItemUiState)
            } catch (e: Exception) {
                _userState.value = UserUiState(errorMessage = e.localizedMessage.replaceNull())

            }
        }
    }

    fun getUserAlbumsWithPhotos(userId: Int) {
        _albumState.value = AlbumUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val data = albumRepository.getAlbumsWithPhotos(userId)
                _albumState.value = AlbumUiState(albums = getAlbumsMap(data))
            } catch (e: Exception) {
                _albumState.value = AlbumUiState(errorMessage = e.localizedMessage.replaceNull())
            }
        }
    }

    private fun getAlbumsMap(data: Map<Album, List<Photo>>): List<AlbumItemUiState> {
        val result = mutableListOf<AlbumItemUiState>()
        data.forEach {
            val albumItemUiState = AlbumItemUiState(
                title = it.key.title.replaceNull(),
                photos = it.value.map {
                    PhotoItemUiState(
                        title = it.title.replaceNull(),
                        url = it.url.replaceNull(),
                        thumbnailUrl = it.thumbnailUrl.replaceNull()
                    )
                })
            result.add(albumItemUiState)
        }
        return result
    }
}