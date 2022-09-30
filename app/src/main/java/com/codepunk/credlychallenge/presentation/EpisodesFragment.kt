package com.codepunk.credlychallenge.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.codepunk.credlychallenge.BuildConfig
import com.codepunk.credlychallenge.databinding.FragmentEpisodesBinding
import com.codepunk.credlychallenge.domain.model.Episode
import com.codepunk.credlychallenge.util.consume
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EpisodesFragment @Inject constructor() : Fragment() {

    // private val args: ShowFragmentArgs by navArgs()

    private val viewModel: EpisodesViewModel by viewModels()

    private lateinit var binding: FragmentEpisodesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentEpisodesBinding.inflate(inflater)
        .apply {
            binding = this
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpCollection()

        val a = arguments
        Log.d("EpisodeFragment", "onViewCreated: arguments=$a")
        val showId = requireArguments().getInt(BuildConfig.KEY_SHOW_ID)
        viewModel.getEpisodes(showId)
    }

    private fun setUpCollection() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loading.collect { onLoading(it) }
                }

                launch {
                    viewModel.episodesResult.collect { onResult(it) }
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

    private fun onResult(result: Result<List<Episode>>) {
        result.onSuccess {
            // TODO
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