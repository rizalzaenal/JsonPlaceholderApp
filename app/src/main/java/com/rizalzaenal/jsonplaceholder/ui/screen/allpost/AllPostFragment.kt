package com.rizalzaenal.jsonplaceholder.ui.screen.allpost

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.rizalzaenal.jsonplaceholder.R
import com.rizalzaenal.jsonplaceholder.databinding.FragmentAllPostBinding
import com.rizalzaenal.jsonplaceholder.ui.base.BaseFragment
import com.rizalzaenal.jsonplaceholder.ui.screen.allpost.state.PostItemUiState
import com.rizalzaenal.jsonplaceholder.ui.screen.postdetail.state.PostDetailItemUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllPostFragment : BaseFragment<FragmentAllPostBinding>() {
    private val viewModel: AllPostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadPosts()
    }

    override fun bind(viewGroup: ViewGroup?): FragmentAllPostBinding {
        return FragmentAllPostBinding.inflate(layoutInflater, viewGroup, false)
    }

    override fun initUi() {
        setupRecyclerView()
        binding.swipeRefresh.setOnRefreshListener { viewModel.loadPosts() }
    }

    override fun collectStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState
                    .collectLatest {
                        binding.swipeRefresh.isRefreshing = it.isLoading
                        (binding.rvPosts.adapter as? AllPostAdapter)?.setItems(it.posts)
                        if (it.errorMessage.isNotEmpty()) {
                            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvPosts.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = AllPostAdapter {
                val args = getPostDetailUiState(it)
                val direction = AllPostFragmentDirections.actionAllPostFragmentToPostDetailFragment(args)
                findNavController().navigate(direction)
            }
            addItemDecoration(
                MaterialDividerItemDecoration(
                    context,
                    MaterialDividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun getPostDetailUiState(data: PostItemUiState): PostDetailItemUiState {
        return PostDetailItemUiState(
            postId = data.postId,
            name = data.name,
            userName = data.username,
            postTitle = data.postTitle,
            postBody = data.postBody
        )
    }
}