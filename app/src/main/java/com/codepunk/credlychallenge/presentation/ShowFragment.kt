package com.codepunk.credlychallenge.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.codepunk.credlychallenge.MainActivity
import com.codepunk.credlychallenge.databinding.FragmentShowBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowFragment : Fragment() {

    private val args: ShowFragmentArgs by navArgs()

    private val viewModel: ShowViewModel by viewModels()

    private lateinit var binding: FragmentShowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentShowBinding.inflate(inflater)
        .apply {
            binding = this
            (requireActivity() as MainActivity).setToolbar(binding.toolbar)
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = args.showName
    }
}