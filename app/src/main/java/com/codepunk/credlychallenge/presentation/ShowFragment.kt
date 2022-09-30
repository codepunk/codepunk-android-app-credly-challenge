package com.codepunk.credlychallenge.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.codepunk.credlychallenge.MainActivity
import com.codepunk.credlychallenge.databinding.FragmentShowBinding
import com.codepunk.credlychallenge.domain.model.Show
import com.codepunk.credlychallenge.util.consume
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        setUpCollection()

        viewModel.getShowInfo(args.showId)
    }

    override fun onResume() {
        super.onResume()
        binding.toolbar.title = args.showName
    }

    private fun setUpCollection() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loading.collect { onLoading(it) }
                }

                launch {
                    viewModel.showResult.collect { onResult(it) }
                }

                launch {
                    viewModel.showError.collect { onError(it) }
                }
            }
        }
    }

    private fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.show()
        } else {
            binding.progressBar.hide()
        }
    }

    private fun onResult(result: Result<Show?>) {
        result.onSuccess {
            // showAdapter.shows = it
            when (val imageUrl = it?.images?.get("original")) {
                null -> binding.showImage.setImageDrawable(null)
                else -> binding.showImage.load(imageUrl)
            }
        }
    }

    private fun onError(error: Lazy<Throwable>?) {
        error?.consume { throwable ->
            Toast
                .makeText(requireContext(), throwable.message, Toast.LENGTH_LONG)
                .show()
        }
    }

}