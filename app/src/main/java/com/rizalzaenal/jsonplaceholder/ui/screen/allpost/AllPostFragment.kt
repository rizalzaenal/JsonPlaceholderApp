package com.rizalzaenal.jsonplaceholder.ui.screen.allpost

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.rizalzaenal.jsonplaceholder.R
import com.rizalzaenal.jsonplaceholder.databinding.FragmentUsersPostBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllPostFragment: Fragment(R.layout.fragment_users_post) {
    private var _binding: FragmentUsersPostBinding? = null
    private val binding: FragmentUsersPostBinding get() = _binding!!
    private val viewModel: AllPostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadPosts()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUsersPostBinding.bind(view)
        setupRecyclerView()
        collectState()
        binding.swipeRefresh.setOnRefreshListener { viewModel.loadPosts() }
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState
                    .collectLatest {
                        binding.swipeRefresh.isRefreshing = it.isLoading
                        (binding.rvPosts.adapter as? AllPostAdapter)?.setItems(it.posts)
                        if (it.errorMessage.isNotEmpty()) {
                            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = AllPostAdapter { Toast.makeText(requireContext(), it.postTitle, Toast.LENGTH_SHORT).show() }
            addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}