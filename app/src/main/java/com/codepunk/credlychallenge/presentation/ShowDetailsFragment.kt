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

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.codepunk.credlychallenge.BuildConfig
import com.codepunk.credlychallenge.MainActivity
import com.codepunk.credlychallenge.R
import com.codepunk.credlychallenge.databinding.FragmentShowDetailsBinding
import com.codepunk.credlychallenge.util.consume
import com.codepunk.credlychallenge.util.showErrorToast
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

// region Constants

const val TAB_COUNT = 2
const val EPISODES_POSITION = 0
const val CAST_POSITION = 1
const val OFFSCREEN_PAGE_LIMIT: Int = 1

// endregion Constants

/**
 * A [Fragment] for displaying the details of given show.
 */
@AndroidEntryPoint
class ShowDetailsFragment : Fragment() {

    // region Properties

    @Inject
    lateinit var episodesFragmentProvider: Provider<EpisodesFragment>

    @Inject
    lateinit var castFragmentProvider: Provider<CastFragment>

    private val args: ShowDetailsFragmentArgs by navArgs()

    private val viewModel: ShowDetailsViewModel by viewModels()

    private lateinit var binding: FragmentShowDetailsBinding

    private lateinit var adapter: ShowPagerAdapter

    // endregion Properties

    // region Lifecycle methods

    /**
     * Inflates the view and creates the view binding.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentShowDetailsBinding
        .inflate(inflater)
        .apply {
            binding = this
            (requireActivity() as MainActivity).setToolbar(binding.toolbar)
        }
        .root

    /**
     * Sets up widgets, begins data collection, and gets the details for the supplied show ID.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ShowPagerAdapter()
        with(binding.viewPager) {
            adapter = this@ShowDetailsFragment.adapter
            offscreenPageLimit = OFFSCREEN_PAGE_LIMIT as Int
            registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        Log.d("ShowFragment", "Here")
                    }
                }
            )

            val mediator = TabLayoutMediator(
                binding.tabLayout,
                this
            ) { currentTab, currentPosition ->
                currentTab.text = when (currentPosition) {
                    EPISODES_POSITION -> getString(R.string.episodes)
                    CAST_POSITION -> getString(R.string.cast)
                    else -> throw IllegalStateException("Invalid view pager position")
                }
            }

            mediator.attach()
        }

        setUpDataCollection()

        if (savedInstanceState == null) {
            viewModel.getShowDetails(args.showId)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.toolbar.title = args.showName
    }

    // endregion Lifecycle methods

    // region Methods

    /**
     * Listens for and responds to changes to data contained in [ShowDetailsViewModel].
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
                    viewModel.showDetails.collect { binding.show = it }
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
     * A [FragmentStateAdapter] that coordinates pages in the show [ViewPager2].
     */
    private inner class ShowPagerAdapter : FragmentStateAdapter(requireActivity()) {

        // region Overridden methods

        override fun getItemCount(): Int = TAB_COUNT

        override fun createFragment(position: Int): Fragment = when (position) {
            EPISODES_POSITION -> episodesFragmentProvider.get().apply {
                this.arguments = Bundle().apply {
                    putInt(BuildConfig.KEY_SHOW_ID, args.showId)
                }
            }
            CAST_POSITION -> castFragmentProvider.get().apply {
                this.arguments = Bundle().apply {
                    putInt(BuildConfig.KEY_SHOW_ID, args.showId)
                }
            }
            else -> throw IllegalStateException("Invalid view pager position")
        }

        // endregion Overridden methods

    }

    // endregion Nested & inner classes

}
