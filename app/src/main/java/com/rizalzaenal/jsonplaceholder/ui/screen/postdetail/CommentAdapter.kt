package com.rizalzaenal.jsonplaceholder.ui.screen.postdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.rizalzaenal.jsonplaceholder.databinding.ItemCommentBinding
import com.rizalzaenal.jsonplaceholder.ui.BasicAdapter
import com.rizalzaenal.jsonplaceholder.ui.screen.postdetail.state.CommentItemUiState

class CommentAdapter: BasicAdapter<CommentItemUiState, ItemCommentBinding>() {
    override fun inflateBinding(parent: ViewGroup): ItemCommentBinding {
        return ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(data: CommentItemUiState, binding: ItemCommentBinding) {
        binding.apply {
            tvCommentBody.text = data.comment
            tvName.text = data.name
        }
    }
}