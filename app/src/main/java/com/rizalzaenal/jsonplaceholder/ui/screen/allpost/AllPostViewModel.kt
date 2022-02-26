package com.rizalzaenal.jsonplaceholder.ui.screen.allpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizalzaenal.jsonplaceholder.data.replaceNull
import com.rizalzaenal.jsonplaceholder.data.repository.UserRepository
import com.rizalzaenal.jsonplaceholder.ui.screen.allpost.state.PostItemUiState
import com.rizalzaenal.jsonplaceholder.ui.screen.allpost.state.PostUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllPostViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<PostUiState>(PostUiState())
    val uiState: StateFlow<PostUiState> = _uiState

    fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = PostUiState(isLoading = true)
            val allPosts = mutableListOf<PostItemUiState>()
            try {
                val userWithPosts = userRepository.getUserWithPost()
                userWithPosts.forEach { mapItem ->
                    mapItem.value.forEach { postItem ->
                        val data = PostItemUiState(
                            name = mapItem.key.name.replaceNull(),
                            username = mapItem.key.username.replaceNull(),
                            companyName = mapItem.key.company?.name.replaceNull(),
                            postTitle = postItem.title.replaceNull(),
                            postBody = postItem.body.replaceNull(),
                            postId = postItem.id.replaceNull()
                        )
                        allPosts.add(data)
                    }
                }
                _uiState.value = PostUiState(posts = allPosts)
            } catch (e: Exception) {
                _uiState.value = PostUiState(errorMessage = e.localizedMessage.replaceNull())
            }
        }
    }

}