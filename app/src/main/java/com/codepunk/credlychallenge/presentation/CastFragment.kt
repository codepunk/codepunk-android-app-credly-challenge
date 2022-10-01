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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.codepunk.credlychallenge.BuildConfig
import com.codepunk.credlychallenge.R
import com.codepunk.credlychallenge.databinding.FragmentCastBinding
import com.codepunk.credlychallenge.databinding.ItemCastEntryBinding
import com.codepunk.credlychallenge.domain.model.CastEntry
import com.codepunk.credlychallenge.util.consume
import com.codepunk.credlychallenge.util.showErrorToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A [Fragment] for displaying a list of cast members for a given show.
 */
@AndroidEntryPoint
class CastFragment @Inject constructor() : Fragment() {

    // region Properties

    private val viewModel: CastViewModel by viewModels()

    private lateinit var binding: FragmentCastBinding

    private val castAdapter = CastAdapter()

    // endregion Properties

    // region Lifecycle methods

    /**
     * Inflates the view and creates the view binding.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCastBinding
        .inflate(inflater)
        .apply { binding = this }
        .root

    /**
     * Sets up widgets, begins data collection, and gets the cast for the supplied show ID.
     */
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

        setUpDataCollection()

        if (savedInstanceState == null) {
            val showId = requireArguments().getInt(BuildConfig.KEY_SHOW_ID)
            viewModel.getCast(showId)
        }
    }

    // endregion Lifecycle methods

    // region Methods

    /**
     * Listens for and responds to changes to data contained in [CastViewModel].
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
                    viewModel.cast.collect { castAdapter.cast = it }
                }

                launch {
                    viewModel.error.collect {
                        it?.consume { requireContext().showErrorToast() }
                    }
                }
            }
        }
    }

    // endregion Methods

    // region Nested & inner classes

    /**
     * A [ViewHolder] used to populating a single item in the cast [RecyclerView].
     */
    private class CastEntryViewHolder(
        val binding: ItemCastEntryBinding
    ) : ViewHolder(binding.root) {

        // region Methods

        /**
         * Binds a [CastEntry] to the view belonging to this [ViewHolder].
         */
        fun bind(castEntry: CastEntry) {
            binding.castEntry = castEntry
        }

        // endregion Methods

    }

    /**
     * A [RecyclerView.Adapter] that coordinates items in the cast [RecyclerView].
     */
    private class CastAdapter : RecyclerView.Adapter<CastEntryViewHolder>() {

        // region Properties

        /**
         * The cast list associated with the current show.
         */
        var cast: List<CastEntry> = emptyList()
            @SuppressLint("NotifyDataSetChanged")
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        // endregion Properties

        // region Overridden methods

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

        // endregion Overridden methods

    }

    // endregion Nested & inner classes

}
