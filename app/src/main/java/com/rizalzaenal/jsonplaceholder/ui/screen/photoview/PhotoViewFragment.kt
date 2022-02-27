package com.rizalzaenal.jsonplaceholder.ui.screen.photoview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.rizalzaenal.jsonplaceholder.databinding.FragmentPhotoViewBinding
import com.rizalzaenal.jsonplaceholder.ui.base.BaseFragment
import com.rizalzaenal.jsonplaceholder.ui.screen.userdetail.getGlideUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoViewFragment: BaseFragment<FragmentPhotoViewBinding>() {
    private val args: PhotoViewFragmentArgs by navArgs()

    override fun bind(viewGroup: ViewGroup?): FragmentPhotoViewBinding {
        return FragmentPhotoViewBinding.inflate(LayoutInflater.from(viewGroup?.context), viewGroup, false)
    }

    override fun initUi() {
        binding.tvTitle.text = args.photoViewUiState.title
        Glide.with(binding.ivPhoto)
            .load(getGlideUrl(args.photoViewUiState.url))
            .into(binding.ivPhoto)
    }

    override fun collectStates() {}
}