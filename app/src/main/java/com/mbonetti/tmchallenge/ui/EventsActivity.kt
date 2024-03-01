package com.mbonetti.tmchallenge.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbonetti.tmchallenge.databinding.ActivityEventsBinding
import com.mbonetti.tmchallenge.db.EventDatabase
import com.mbonetti.tmchallenge.repository.EventRepository
import com.mbonetti.tmchallenge.ui.adapters.EventAdapter
import com.mbonetti.tmchallenge.util.Constants.Companion.QUERY_PAGE_SIZE
import com.mbonetti.tmchallenge.util.Constants.Companion.SEARCH_DELAY
import com.mbonetti.tmchallenge.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EventsActivity : AppCompatActivity() {

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    lateinit var viewModel: EventViewModel
    private lateinit var eventAdapter: EventAdapter
    private lateinit var binding: ActivityEventsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventRepository = EventRepository(EventDatabase(context = this))
        val viewModelProviderFactory = EventViewModelProviderFactory(application, eventRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[EventViewModel::class.java]

        setupRecyclerView()

        var job: Job? = null
        binding.search.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_DELAY)
                editable?.let {
                    if (editable.toString().isEmpty()) {
                        viewModel.getEvents()
                    } else {
                        viewModel.searchEventsByKeywordOrCity(editable.toString())
                    }
                }
            }
        }

        viewModel.isSearchByCityChecked.observe(this) { isSearchByCityChecked ->
            binding.checkBox.isChecked = isSearchByCityChecked
        }

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setSearchByCityChecked(isChecked)
        }

        viewModel.events.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { eventsResponse ->
                        eventAdapter.differ.submitList(eventsResponse.embedded?.events?.toList())
                        val totalPages = eventsResponse.page?.totalPages?.div(QUERY_PAGE_SIZE + 2)
                        isLastPage = viewModel.eventsPage == totalPages
                        if (isLastPage) {
                            binding.rvEvents.setPadding(0, 0, 0, 0)
                        }
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(this, "An error occurred: $message", Toast.LENGTH_LONG).show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

        eventAdapter.setOnItemClickListener { event ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.url))
            startActivity(intent)
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage
                    && isAtLastItem
                    && isNotAtBeginning
                    && isTotalMoreThanVisible
                    && isScrolling

            if (shouldPaginate) {
                viewModel.getEvents()
                isScrolling = false
            }
        }
    }

    private fun setupRecyclerView() {
        eventAdapter = EventAdapter()
        binding.rvEvents.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(this@EventsActivity)
            addOnScrollListener(this@EventsActivity.scrollListener)
        }
    }
}
