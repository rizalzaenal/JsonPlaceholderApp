package com.rizalzaenal.jsonplaceholder.ui.screen.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizalzaenal.jsonplaceholder.data.replaceNull
import com.rizalzaenal.jsonplaceholder.data.repository.PostRepository
import com.rizalzaenal.jsonplaceholder.ui.screen.allpost.state.PostUiState
import com.rizalzaenal.jsonplaceholder.ui.screen.postdetail.state.CommentItemUiState
import com.rizalzaenal.jsonplaceholder.ui.screen.postdetail.state.CommentUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(private val postRepository: PostRepository): ViewModel() {
    private val _commentUiState = MutableStateFlow<CommentUiState>(CommentUiState())
    val commentUiState: StateFlow<CommentUiState> = _commentUiState

    fun loadComments(postId: Int) {
        viewModelScope.launch {
            _commentUiState.value = CommentUiState(isLoading = true)
            try {
                val response = postRepository.getPostComments(postId)
                val comments = response.map { CommentItemUiState(it.body.replaceNull(), name = it.name.replaceNull()) }
                _commentUiState.value = CommentUiState(comments = comments)
            } catch (e: Exception) {
                _commentUiState.value = CommentUiState(errorMessage = e.localizedMessage.replaceNull())
            }
        }
    }

}