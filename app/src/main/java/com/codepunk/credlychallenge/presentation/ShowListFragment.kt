/*
 * Copyright (C) 2022 Scott Slater
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.codepunk.credlychallenge.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.codepunk.credlychallenge.databinding.FragmentShowListBinding
import com.codepunk.credlychallenge.databinding.ItemShowBinding
import com.codepunk.credlychallenge.domain.model.Show
import com.codepunk.credlychallenge.util.consume
import com.codepunk.credlychallenge.util.showErrorToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A [Fragment] for displaying a list of shows.
 */
@AndroidEntryPoint
class ShowListFragment : Fragment() {

    // region Properties

    private val viewModel: ShowListViewModel by viewModels()

    private lateinit var binding: FragmentShowListBinding

    private val showAdapter = ShowAdapter(
        OnClickListener {
            onShowClicked(it)
        }
    )

    // endregion Properties

    // region Lifecycle methods

    /**
     * Inflates the view and creates the view binding.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentShowListBinding
        .inflate(inflater)
        .apply {
            binding = this
            (requireActivity() as MainActivity).setToolbar(binding.toolbar)
        }
        .root

    /**
     * Sets up widgets, begins data collection, and gets the list of shows defined in
     * [ShowListViewModel].
     */
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

        setUpDataCollection()

        if (savedInstanceState == null) {
            viewModel.getDefaultShows()
        }
    }

    // endregion Lifecycle methods

    // region Methods

    /**
     * Listens for and responds to changes to data contained in [ShowListViewModel].
     */
    private fun setUpDataCollection() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loading.collect { isLoading ->
                        binding.progressBar.apply { if (isLoading) show() else hide() }
                    }
                }

                launch {
                    viewModel.shows.collect { showAdapter.shows = it }
                }

                launch {
                    viewModel.error.collect {
                        it?.consume { requireContext().showErrorToast() }
                    }
                }
            }
        }
    }

    /**
     * Responds to an item in the show [RecyclerView] being clicked.
     */
    private fun onShowClicked(show: Show) {
        val action = ShowListFragmentDirections.actionShowListToShowDetails(show.id, show.name)
        findNavController().navigate(action)
    }

    // endregion Methods

    // region Nested & inner classes

    /**
     * A class that handles item clicks in the show [RecyclerView].
     */
    private class OnClickListener(val clickListener: (show: Show) -> Unit) {

        // region Methods

        fun onClick(show: Show) = clickListener(show)

        // endregion Methods

    }

    /**
     * A [ViewHolder] used to populating a single item in the show [RecyclerView].
     */
    private class ShowViewHolder(val binding: ItemShowBinding) : ViewHolder(binding.root) {

        // region Methods

        fun bind(show: Show) {
            binding.show = show
        }

        // endregion Methods

    }

    /**
     * A [RecyclerView.Adapter] that coordinates items in the show [RecyclerView].
     */
    private class ShowAdapter(
        private val onClickListener: OnClickListener
    ) : Adapter<ShowViewHolder>() {

        // region Properties

        /**
         * The initial list of shows presented by this application.
         */
        var shows: List<Show> = emptyList()
            @SuppressLint("NotifyDataSetChanged")
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        // endregion Properties

        // region Overridden methods

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

        // endregion Overridden methods

    }

    // endregion Nested & inner classes

}
