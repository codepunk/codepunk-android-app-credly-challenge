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
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.codepunk.credlychallenge.R
import com.codepunk.credlychallenge.databinding.FragmentMainBinding
import com.codepunk.credlychallenge.databinding.ItemShowBinding
import com.codepunk.credlychallenge.domain.model.Show
import com.codepunk.credlychallenge.util.consume
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: FragmentMainBinding

    private val showAdapter = ShowAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainBinding.inflate(inflater)
        .apply {
            binding = this
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.showRecycler) {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = showAdapter
        }

        collectValues()

        if (savedInstanceState == null) {
            viewModel.getDefaultShows()
        }
    }

    private fun collectValues() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loading.collect { onLoading(it) }
                }

                launch {
                    viewModel.showsResult.collect { onResult(it) }
                }

                launch {
                    viewModel.showsError.collect { onError(it) }
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

    private fun onResult(result: Result<List<Show>>) {
        result.onSuccess {
            showAdapter.shows = it
        }
    }

    private fun onError(error: Lazy<Throwable>?) {
        error?.consume { throwable ->
            Toast
                .makeText(requireContext(), throwable.message, Toast.LENGTH_LONG)
                .show()
        }
    }

    private class ShowViewHolder(private val binding: ItemShowBinding) : ViewHolder(binding.root) {
        fun bind(show: Show) {
            binding.show = show
        }
    }

    private class ShowAdapter : Adapter<ShowViewHolder>() {

        var shows: List<Show> = emptyList()
            @SuppressLint("NotifyDataSetChanged")
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getItemCount(): Int = shows.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
            val binding = DataBindingUtil.inflate<ItemShowBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_show,
                parent,
                false
            )
            return ShowViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
            shows.getOrNull(position)?.also {
                holder.bind(it)
            }
        }

    }

}