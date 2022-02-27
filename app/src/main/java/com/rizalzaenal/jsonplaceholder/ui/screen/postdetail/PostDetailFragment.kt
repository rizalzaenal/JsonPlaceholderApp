package com.rizalzaenal.jsonplaceholder.ui.screen.postdetail

import android.os.Bundle
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
import com.rizalzaenal.jsonplaceholder.databinding.FragmentPostDetailBinding
import com.rizalzaenal.jsonplaceholder.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailFragment : BaseFragment<FragmentPostDetailBinding>() {
    private val viewModel: PostDetailViewModel by viewModels()
    private val args: PostDetailFragmentArgs by navArgs()

    override fun bind(viewGroup: ViewGroup?): FragmentPostDetailBinding {
        return FragmentPostDetailBinding.inflate(layoutInflater, viewGroup, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadComments(args.postDetailUiState.postId)
    }

    override fun initUi() {
        setupRecyclerView()

        binding.apply {
            tvTitle.text = args.postDetailUiState.postTitle
            tvName.text = args.postDetailUiState.name
            tvUsername.text =
                getString(R.string.with_parenthesis, args.postDetailUiState.userName)
            tvBody.text = args.postDetailUiState.postBody
            tvName.setOnClickListener {
                val direction = PostDetailFragmentDirections
                    .actionPostDetailFragmentToUserDetailFragment(args.postDetailUiState.userId)
                findNavController().navigate(direction)
            }
        }
    }

    override fun collectStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.commentUiState
                    .collectLatest {
                        binding.progressBar.visibility =
                            if (it.isLoading) View.VISIBLE else View.GONE
                        (binding.rvComments.adapter as? CommentAdapter)?.setItems(it.comments)
                        if (it.errorMessage.isNotEmpty()) {
                            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvComments.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = CommentAdapter()
        }
    }

}