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
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.codepunk.credlychallenge.BuildConfig
import com.codepunk.credlychallenge.MainActivity
import com.codepunk.credlychallenge.R
import com.codepunk.credlychallenge.databinding.FragmentShowBinding
import com.codepunk.credlychallenge.domain.model.Show
import com.codepunk.credlychallenge.util.consume
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

const val TAB_COUNT = 2
const val EPISODES_POSITION = 0
const val CAST_POSITION = 1
const val OFFSCREEN_PAGE_LIMIT: Int = 1

@AndroidEntryPoint
class ShowFragment : Fragment() {

    @Inject
    lateinit var episodesFragmentProvider: Provider<EpisodesFragment>

    @Inject
    lateinit var castFragmentProvider: Provider<CastFragment>

    private val args: ShowFragmentArgs by navArgs()

    private val viewModel: ShowViewModel by viewModels()

    private lateinit var binding: FragmentShowBinding

    private lateinit var adapter: ShowPagerAdapter

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

        adapter = ShowPagerAdapter()
        with(binding.viewPager) {
            adapter = this@ShowFragment.adapter
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
            when (val imageUrl = it?.images?.original) {
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

    private inner class ShowPagerAdapter() : FragmentStateAdapter(requireActivity()) {

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

    }

}