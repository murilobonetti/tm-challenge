package com.mbonetti.tmchallenge.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mbonetti.tmchallenge.databinding.EventListItemBinding
import com.mbonetti.tmchallenge.db.models.Event

class EventAdapter : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    inner class EventViewHolder(private val binding: EventListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String, eventName: String) {
            Glide.with(binding.root).load(imageUrl).into(binding.eventImage)
            binding.eventName.text = eventName
//            binding.eventNotice.text = notice
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = EventListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return EventViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = differ.currentList[position]
        holder.bind(
            imageUrl = event.images[0].url,
            eventName = event.name,
//            notice = event.pleaseNote
        )

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { listener -> listener(event) }
        }
    }

    private var onItemClickListener: ((Event) -> Unit)? = null

    fun setOnItemClickListener(listener: (Event) -> Unit) {
        onItemClickListener = listener
    }


}
