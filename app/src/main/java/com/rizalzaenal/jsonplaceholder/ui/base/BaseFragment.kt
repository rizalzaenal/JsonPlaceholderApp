package com.rizalzaenal.jsonplaceholder.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding>(): Fragment() {
    protected var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    abstract fun bind(viewGroup: ViewGroup?): VB
    abstract fun initUi()
    abstract fun collectStates()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        collectStates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}