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
import com.codepunk.credlychallenge.databinding.FragmentCastBinding
import com.codepunk.credlychallenge.databinding.ItemCastEntryBinding
import com.codepunk.credlychallenge.databinding.ItemEpisodeBinding
import com.codepunk.credlychallenge.domain.model.CastEntry
import com.codepunk.credlychallenge.domain.model.Episode
import com.codepunk.credlychallenge.util.consume
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CastFragment @Inject constructor() : Fragment() {

    // private val args: ShowFragmentArgs by navArgs()

    private val viewModel: CastViewModel by viewModels()

    private lateinit var binding: FragmentCastBinding

    private val castAdapter = CastAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCastBinding.inflate(inflater)
        .apply {
            binding = this
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.castRecycler) {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = castAdapter
        }

        setUpCollection()

        val showId = requireArguments().getInt(BuildConfig.KEY_SHOW_ID)
        viewModel.getCast(showId)
    }

    private fun setUpCollection() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loading.collect { onLoading(it) }
                }

                launch {
                    viewModel.castResult.collect { onResult(it) }
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

    private fun onResult(result: Result<List<CastEntry>>) {
        result.onSuccess {
            castAdapter.cast = it
        }
    }

    private fun onError(error: Lazy<Throwable>?) {
        error?.consume { throwable ->
            Toast
                .makeText(requireContext(), throwable.message, Toast.LENGTH_LONG)
                .show()
        }
    }

    private class CastEntryViewHolder(val binding: ItemCastEntryBinding) : ViewHolder(binding.root) {
        fun bind(castEntry: CastEntry) {
            binding.castEntry = castEntry
        }
    }

    private class CastAdapter() : RecyclerView.Adapter<CastEntryViewHolder>() {

        var cast: List<CastEntry> = emptyList()
            @SuppressLint("NotifyDataSetChanged")
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getItemCount(): Int = cast.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastEntryViewHolder {
            val binding = DataBindingUtil.inflate<ItemCastEntryBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_cast_entry,
                parent,
                false
            )

            return CastEntryViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CastEntryViewHolder, position: Int) {
            cast.getOrNull(position)?.also { castEntry ->
                holder.bind(castEntry)
            }
        }

    }

}