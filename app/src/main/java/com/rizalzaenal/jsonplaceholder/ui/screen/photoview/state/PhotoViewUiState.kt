package com.rizalzaenal.jsonplaceholder.ui.screen.photoview.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoViewUiState(
    val title: String = "",
    val url: String = ""
): Parcelable
