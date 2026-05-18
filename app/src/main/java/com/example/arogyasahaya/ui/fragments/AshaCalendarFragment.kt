package com.example.arogyasahaya.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arogyasahaya.R
import com.example.arogyasahaya.databinding.FragmentAshaCalendarBinding
import com.example.arogyasahaya.ui.adapters.AshaEvent
import com.example.arogyasahaya.ui.adapters.AshaEventAdapter
import java.util.Calendar

class AshaCalendarFragment : Fragment() {

    private var _binding: FragmentAshaCalendarBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var eventMap: Map<String, List<AshaEvent>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAshaCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSimulatedEvents()

        binding.rvAshaEvents.layoutManager = LinearLayoutManager(context)

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val dateKey = "$dayOfMonth/$month/$year"
            val events = eventMap[dateKey] ?: emptyList()
            updateEventList(events)
        }
        
        val today = Calendar.getInstance()
        val initialKey = "${today.get(Calendar.DAY_OF_MONTH)}/${today.get(Calendar.MONTH)}/${today.get(Calendar.YEAR)}"
        updateEventList(eventMap[initialKey] ?: emptyList())
    }

    private fun setupSimulatedEvents() {
        val cal = Calendar.getInstance()
        val currentMonth = cal.get(Calendar.MONTH)
        val currentYear = cal.get(Calendar.YEAR)

        // Using translated strings for all simulated events
        eventMap = mapOf(
            "10/$currentMonth/$currentYear" to listOf(AshaEvent(getString(R.string.event_vax_title), "Date: 10th", getString(R.string.event_vax_desc))),
            "15/$currentMonth/$currentYear" to listOf(AshaEvent(getString(R.string.event_visit_title), "Date: 15th", getString(R.string.event_visit_desc))),
            "20/$currentMonth/$currentYear" to listOf(AshaEvent(getString(R.string.event_eye_title), "Date: 20th", getString(R.string.event_eye_desc))),
            "25/$currentMonth/$currentYear" to listOf(AshaEvent(getString(R.string.event_nutri_title), "Date: 25th", getString(R.string.event_nutri_desc)))
        )
    }
    
    private fun updateEventList(events: List<AshaEvent>) {
        binding.rvAshaEvents.adapter = AshaEventAdapter(events)
        if (events.isEmpty()) {
            binding.tvSelectedDateEvents.text = getString(R.string.no_events_today)
        } else {
            binding.tvSelectedDateEvents.text = getString(R.string.events_on_this_day)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
