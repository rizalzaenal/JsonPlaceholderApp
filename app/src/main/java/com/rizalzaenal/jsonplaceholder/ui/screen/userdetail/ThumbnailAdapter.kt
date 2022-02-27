package com.rizalzaenal.jsonplaceholder.ui.screen.userdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.rizalzaenal.jsonplaceholder.databinding.ItemThumbnailBinding
import com.rizalzaenal.jsonplaceholder.ui.BasicAdapter
import com.rizalzaenal.jsonplaceholder.ui.screen.userdetail.state.PhotoItemUiState

class ThumbnailAdapter(private val onItemClick: (PhotoItemUiState) -> Unit): BasicAdapter<PhotoItemUiState, ItemThumbnailBinding>() {
    override fun inflateBinding(parent: ViewGroup): ItemThumbnailBinding {
        return ItemThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(data: PhotoItemUiState, binding: ItemThumbnailBinding) {
        binding.apply {
            Glide.with(binding.ivThumbnail)
                .load(getGlideUrl(data.thumbnailUrl))
                .into(binding.ivThumbnail)
            binding.ivThumbnail.setOnClickListener { onItemClick(data) }
        }
    }

    private fun getGlideUrl(url: String): GlideUrl {
        /*Fix issue with https://via.placeholder.com when loading image
        see: https://stackoverflow.com/a/62426188/6857577
        */
        return GlideUrl(url, LazyHeaders
            .Builder()
            .addHeader("User-Agent", "your-user-agent")
            .build()
        )
    }
}