package com.codepunk.credlychallenge.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.codepunk.credlychallenge.R
import com.google.android.material.progressindicator.CircularProgressIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private val queryEdit: EditText by lazy {
        requireView().findViewById(R.id.query_edit)
    }

    private val searchButton: Button by lazy {
        requireView().findViewById(R.id.search_button)
    }

    private val progressBar: ProgressBar by lazy {
        requireView().findViewById(R.id.progress_bar)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.getDefaultShows()
        }

        searchButton.setOnClickListener {
            onSearch()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loading.collect { loading ->
                        if (loading) {
                            progressBar.visibility = View.VISIBLE
                        } else {
                            progressBar.visibility = View.GONE
                        }
                        Log.d("MainFragment", "loading=$loading")
                    }
                }

                launch {
                    viewModel.searchResults.collect { list ->
                        Log.d("MainFragment", "list=$list")
                    }
                }

                launch {
                    viewModel.searchError.collect { lazy ->
                        Log.e("MainFragment", "lazy=$lazy")
                        lazy?.run {
                            val displayError = !isInitialized()
                            val throwable = lazy.value
                            if (displayError) {
                                Toast
                                    .makeText(
                                        requireContext(),
                                        throwable.message,
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                                Log.d("MainFragment", throwable.message, throwable)
                            }
                        }
                    }
                }
            }
        }
    }

    fun onSearch() {
        val query = queryEdit.text.toString()
        viewModel.searchShows(query)
    }

}