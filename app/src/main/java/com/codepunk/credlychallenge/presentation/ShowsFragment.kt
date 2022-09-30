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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.codepunk.credlychallenge.MainActivity
import com.codepunk.credlychallenge.R
import com.codepunk.credlychallenge.databinding.FragmentShowsBinding
import com.codepunk.credlychallenge.databinding.ItemShowBinding
import com.codepunk.credlychallenge.domain.model.Show
import com.codepunk.credlychallenge.util.consume
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowsFragment : Fragment() {

    private val viewModel: ShowsViewModel by viewModels()

    private lateinit var binding: FragmentShowsBinding

    private val showAdapter = ShowAdapter(
        OnClickListener {
            onShowClicked(it)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentShowsBinding.inflate(inflater)
        .apply {
            binding = this
            (requireActivity() as MainActivity).setToolbar(binding.toolbar)
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

        setUpCollection()

        if (savedInstanceState == null) {
            viewModel.getDefaultShows()
        }
    }

    private fun setUpCollection() {
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

    private fun onShowClicked(show: Show) {
        val action = ShowsFragmentDirections.actionShowsToShow(show.id, show.name)
        findNavController().navigate(action)
    }

    private class OnClickListener(val clickListener: (show: Show) -> Unit) {
        fun onClick(show: Show) = clickListener(show)
    }

    private class ShowViewHolder(val binding: ItemShowBinding) : ViewHolder(binding.root) {
        fun bind(show: Show) {
            binding.show = show
        }
    }

    private class ShowAdapter(
        private val onClickListener: OnClickListener
    ) : Adapter<ShowViewHolder>() {

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
            shows.getOrNull(position)?.also { show ->
                holder.bind(show)
                holder.binding.root.setOnClickListener {
                    onClickListener.onClick(show)
                }
            }
        }

    }

}