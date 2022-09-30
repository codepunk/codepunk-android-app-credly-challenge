package com.codepunk.credlychallenge.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.codepunk.credlychallenge.BuildConfig
import com.codepunk.credlychallenge.R
import com.codepunk.credlychallenge.databinding.FragmentEpisodesBinding
import com.codepunk.credlychallenge.databinding.ItemEpisodeBinding
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

    private val episodeAdapter = EpisodeAdapter()

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

        with(binding.episodesRecycler) {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = episodeAdapter
        }

        setUpCollection()

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
            episodeAdapter.episodes = it
        }
    }

    private fun onError(error: Lazy<Throwable>?) {
        error?.consume { throwable ->
            Toast
                .makeText(requireContext(), throwable.message, Toast.LENGTH_LONG)
                .show()
        }
    }

    private class EpisodeViewHolder(val binding: ItemEpisodeBinding) : ViewHolder(binding.root) {
        fun bind(episode: Episode) {
            binding.episode = episode
        }
    }

    private class EpisodeAdapter() : RecyclerView.Adapter<EpisodeViewHolder>() {

        var episodes: List<Episode> = emptyList()
            @SuppressLint("NotifyDataSetChanged")
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getItemCount(): Int = episodes.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
            val binding = DataBindingUtil.inflate<ItemEpisodeBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_episode,
                parent,
                false
            )

            return EpisodeViewHolder(binding)
        }

        override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
            episodes.getOrNull(position)?.also { episode ->
                holder.bind(episode)
            }
        }

    }

}