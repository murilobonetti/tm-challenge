package com.mbonetti.tmchallenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbonetti.tmchallenge.databinding.ActivityEventsBinding
import com.mbonetti.tmchallenge.db.EventDatabase
import com.mbonetti.tmchallenge.repository.EventRepository
import com.mbonetti.tmchallenge.ui.adapters.EventAdapter
import com.mbonetti.tmchallenge.util.Resource

class EventsActivity : AppCompatActivity() {

    lateinit var viewModel: EventViewModel
    lateinit var eventAdapter: EventAdapter
    private lateinit var binding: ActivityEventsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventRepository = EventRepository(EventDatabase(context = this))
        val viewModelProviderFactory = EventViewModelProviderFactory(eventRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[EventViewModel::class.java]

        setupRecyclerView()

        viewModel.events.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { eventsResponse ->
                        eventAdapter.differ.submitList(eventsResponse.embedded.events)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {message ->
                        Log.e("EventsActivity", "Something went wrong: $message")
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        eventAdapter = EventAdapter()
        binding.rvEvents.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(this@EventsActivity)
        }
    }
}
