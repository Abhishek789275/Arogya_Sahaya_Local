package com.example.arogyasahaya.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import com.example.arogyasahaya.databinding.FragmentMedicationReminderBinding
import com.example.arogyasahaya.ui.adapters.ReminderAdapter
import com.example.arogyasahaya.ui.viewmodel.MainViewModel

class MedicationReminderFragment : Fragment() {

    private var _binding: FragmentMedicationReminderBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: ReminderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicationReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ReminderAdapter(emptyList(),
            onTakeClick = { med ->
                val updatedMed = med.copy(isTaken = true)
                viewModel.updateMedication(updatedMed)
                Toast.makeText(context, "Marked as Taken", Toast.LENGTH_SHORT).show()
            },
            onSkipClick = { med ->
                Toast.makeText(context, "Medication Skipped", Toast.LENGTH_SHORT).show()
            }
        )
        binding.rvReminders.layoutManager = LinearLayoutManager(context)
        binding.rvReminders.adapter = adapter

        viewModel.allMedications.observe(viewLifecycleOwner) { medications ->
            // In a real app, filter for today's upcoming doses
            adapter.updateData(medications)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
