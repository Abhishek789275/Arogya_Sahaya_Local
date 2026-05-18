package com.example.arogyasahaya.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.arogyasahaya.R

data class AshaEvent(val title: String, val date: String, val description: String)

class AshaEventAdapter(private val events: List<AshaEvent>) :
    RecyclerView.Adapter<AshaEventAdapter.EventViewHolder>() {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvEventTitle)
        val tvDate: TextView = view.findViewById(R.id.tvEventDate)
        val tvDesc: TextView = view.findViewById(R.id.tvEventDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_asha_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.tvTitle.text = event.title
        holder.tvDate.text = event.date
        holder.tvDesc.text = event.description
    }

    override fun getItemCount() = events.size
}
