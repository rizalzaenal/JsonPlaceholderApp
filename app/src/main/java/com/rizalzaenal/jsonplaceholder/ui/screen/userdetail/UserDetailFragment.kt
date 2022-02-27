package com.rizalzaenal.jsonplaceholder.ui.screen.userdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizalzaenal.jsonplaceholder.R
import com.rizalzaenal.jsonplaceholder.databinding.FragmentUserDetailBinding
import com.rizalzaenal.jsonplaceholder.ui.base.BaseFragment
import com.rizalzaenal.jsonplaceholder.ui.screen.photoview.state.PhotoViewUiState
import com.rizalzaenal.jsonplaceholder.ui.screen.userdetail.state.UserUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailFragment : BaseFragment<FragmentUserDetailBinding>() {
    private val viewModel: UserDetailViewModel by viewModels()
    private val args: UserDetailFragmentArgs by navArgs()

    override fun bind(viewGroup: ViewGroup?): FragmentUserDetailBinding {
        return FragmentUserDetailBinding.inflate(layoutInflater, viewGroup, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUser(args.userId)
        viewModel.getUserAlbumsWithPhotos(args.userId)
    }

    override fun initUi() {
        setupRecyclerView()
    }

    override fun collectStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.userState
                    .collectLatest {
                        binding.userDetailProgressBar.visibility =
                            if (it.isLoading) View.VISIBLE else View.GONE
                        with(it.user) {
                            val username = userName
                            binding.tvName.text = name
                            binding.tvUsername.text = if (username.isNotEmpty()) getString(
                                R.string.with_parenthesis, userName
                            ) else ""
                            binding.tvEmail.text = email
                            binding.tvCompany.text = company
                            binding.tvAddress.text = address
                        }

                        if (it.errorMessage.isNotEmpty()) {
                            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.albumState
                    .collectLatest {
                        binding.albumProgressBar.visibility =
                            if (it.isLoading) View.VISIBLE else View.GONE
                        (binding.rvAlbum.adapter as? AlbumAdapter)?.setItems(it.albums)
                        if (it.errorMessage.isNotEmpty()) {
                            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvAlbum.layoutManager =
                LinearLayoutManager(rvAlbum.context, LinearLayoutManager.VERTICAL, false)
            rvAlbum.adapter = AlbumAdapter {
                val direction =
                    UserDetailFragmentDirections.actionUserDetailFragmentToPhotoViewFragment(
                        PhotoViewUiState(title = it.title, url = it.url)
                    )
                findNavController().navigate(direction)
            }
        }
    }
}