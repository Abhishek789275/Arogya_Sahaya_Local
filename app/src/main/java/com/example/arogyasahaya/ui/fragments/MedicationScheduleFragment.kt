package com.example.arogyasahaya.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arogyasahaya.data.entities.Medication
import com.example.arogyasahaya.databinding.FragmentMedicationScheduleBinding
import com.example.arogyasahaya.ui.adapters.MedicationAdapter
import com.example.arogyasahaya.ui.viewmodel.MainViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar
import java.util.Locale

class MedicationScheduleFragment : Fragment() {

    private var _binding: FragmentMedicationScheduleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: MedicationAdapter
    
    private var selectedHour = 8
    private var selectedMinute = 0
    private var isTimeSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicationScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MedicationAdapter(emptyList()) { med ->
            viewModel.deleteMedication(med)
            android.widget.Toast.makeText(context, "Medication deleted", android.widget.Toast.LENGTH_SHORT).show()
        }
        binding.rvMedications.layoutManager = LinearLayoutManager(context)
        binding.rvMedications.adapter = adapter

        binding.btnSelectTime.setOnClickListener {
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(selectedHour)
                .setMinute(selectedMinute)
                .setTitleText("Select Medication Time")
                .build()

            picker.addOnPositiveButtonClickListener {
                selectedHour = picker.hour
                selectedMinute = picker.minute
                isTimeSelected = true
                val amPm = if (selectedHour >= 12) "PM" else "AM"
                val hour12 = if (selectedHour % 12 == 0) 12 else selectedHour % 12
                binding.btnSelectTime.text = String.format(Locale.getDefault(), "%02d:%02d %s", hour12, selectedMinute, amPm)
            }
            picker.show(parentFragmentManager, "TIME_PICKER")
        }

        binding.btnAddMed.setOnClickListener {
            addMedication()
        }

        viewModel.allMedications.observe(viewLifecycleOwner) { medications ->
            adapter.updateData(medications)
        }
    }

    private fun addMedication() {
        val name = binding.etMedName.text.toString()
        val dosage = binding.etDosage.text.toString()
        
        val timeOfDay = when (binding.rgTimeOfDay.checkedRadioButtonId) {
            binding.rbMorning.id -> "Morning"
            binding.rbAfternoon.id -> "Afternoon"
            binding.rbNight.id -> "Night"
            else -> ""
        }

        if (name.isNotEmpty() && dosage.isNotEmpty() && timeOfDay.isNotEmpty() && isTimeSelected) {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, selectedHour)
                set(Calendar.MINUTE, selectedMinute)
                set(Calendar.SECOND, 0)
                if (before(Calendar.getInstance())) {
                    add(Calendar.DATE, 1)
                }
            }
            val timeInMillis = calendar.timeInMillis
            val specificTime = binding.btnSelectTime.text.toString()

            val med = Medication(
                medicineName = name, 
                dosage = dosage, 
                timeOfDay = timeOfDay, 
                specificTime = specificTime, 
                timeInMillis = timeInMillis
            )
            viewModel.insertMedication(med)
            
            // Schedule the alarm/worker here using timeInMillis if needed
            
            binding.etMedName.text?.clear()
            binding.etDosage.text?.clear()
            binding.rgTimeOfDay.clearCheck()
            binding.btnSelectTime.text = "Select Exact Time"
            isTimeSelected = false
            
            Toast.makeText(context, "Medication added!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
