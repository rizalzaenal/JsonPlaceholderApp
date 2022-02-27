package com.rizalzaenal.jsonplaceholder.ui.screen.userdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizalzaenal.jsonplaceholder.databinding.ItemAlbumBinding
import com.rizalzaenal.jsonplaceholder.ui.BasicAdapter
import com.rizalzaenal.jsonplaceholder.ui.screen.userdetail.state.AlbumItemUiState
import com.rizalzaenal.jsonplaceholder.ui.screen.userdetail.state.PhotoItemUiState

class AlbumAdapter(private val onItemClick: (PhotoItemUiState) -> Unit) :
    BasicAdapter<AlbumItemUiState, ItemAlbumBinding>() {
    override fun inflateBinding(parent: ViewGroup): ItemAlbumBinding {
        return ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(data: AlbumItemUiState, binding: ItemAlbumBinding) {
        binding.apply {
            tvTitle.text = data.title
            setupRecyclerView(rvThumbnail)
            (rvThumbnail.adapter as? ThumbnailAdapter)?.setItems(data.photos)
        }
    }

    private fun setupRecyclerView(rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(rv.context, LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = ThumbnailAdapter(onItemClick)
    }
}