package com.rizalzaenal.jsonplaceholder.ui.screen.allpost

import android.view.LayoutInflater
import android.view.ViewGroup
import com.rizalzaenal.jsonplaceholder.databinding.ItemPostBinding
import com.rizalzaenal.jsonplaceholder.ui.BasicAdapter
import com.rizalzaenal.jsonplaceholder.ui.screen.allpost.state.PostItemUiState

class AllPostAdapter(private val onItemClick: (PostItemUiState) -> Unit) :
    BasicAdapter<PostItemUiState, ItemPostBinding>() {

    override fun inflateBinding(parent: ViewGroup): ItemPostBinding {
        return ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(data: PostItemUiState, binding: ItemPostBinding) {
        binding.apply {
            tvTitle.text = data.postTitle
            tvBody.text = data.postBody
            tvUsername.text = data.username
            tvCompanyName.text = data.companyName
            cardView.setOnClickListener { onItemClick(data) }
        }
    }
}