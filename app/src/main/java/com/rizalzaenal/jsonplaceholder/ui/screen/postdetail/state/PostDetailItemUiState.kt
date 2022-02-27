package com.rizalzaenal.jsonplaceholder.ui.screen.postdetail.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostDetailItemUiState(
    val postId: Int = 0,
    val userId: Int = 0,
    val name: String = "",
    val userName: String = "",
    val postTitle: String = "",
    val postBody: String = "",
): Parcelable
