package com.example.arogyasahaya.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.arogyasahaya.data.entities.Vitals
import com.example.arogyasahaya.databinding.FragmentVitalLogBinding
import com.example.arogyasahaya.ui.viewmodel.MainViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import android.graphics.Color
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class VitalLogFragment : Fragment() {

    private var _binding: FragmentVitalLogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVitalLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveVitals.setOnClickListener {
            saveVitals()
        }

        viewModel.recentVitals.observe(viewLifecycleOwner) { vitalsList ->
            setupChart(vitalsList)
        }
    }

    private fun saveVitals() {
        val bp = binding.etBloodPressure.text.toString()
        val hrString = binding.etHeartRate.text.toString()
        val sugarString = binding.etBloodSugar.text.toString()
        val weightString = binding.etWeight.text.toString()

        if (bp.isNotEmpty() && hrString.isNotEmpty()) {
            val hr = hrString.toIntOrNull() ?: 0
            val sugar = sugarString.toFloatOrNull() ?: 0f
            val weight = weightString.toFloatOrNull() ?: 0f
            val newVitals = Vitals(bloodPressure = bp, heartRate = hr, bloodSugar = sugar, weight = weight)
            viewModel.insertVitals(newVitals)
            binding.etBloodPressure.text?.clear()
            binding.etHeartRate.text?.clear()
            binding.etBloodSugar.text?.clear()
            binding.etWeight.text?.clear()
            Toast.makeText(context, "Vitals saved successfully!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Please enter at least BP and Heart Rate", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupChart(vitalsList: List<Vitals>) {
        if (vitalsList.isEmpty()) return

        val hrEntries = mutableListOf<Entry>()
        val sysEntries = mutableListOf<Entry>()
        val diaEntries = mutableListOf<Entry>()
        val dates = mutableListOf<String>()
        val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
        
        // Reverse to show chronological order (Room returns DESC timestamp)
        vitalsList.reversed().forEachIndexed { index, vitals ->
            hrEntries.add(Entry(index.toFloat(), vitals.heartRate.toFloat()))
            dates.add(dateFormat.format(Date(vitals.timestamp)))
            
            val bpParts = vitals.bloodPressure.split("/")
            if (bpParts.size == 2) {
                val sys = bpParts[0].toFloatOrNull() ?: 0f
                val dia = bpParts[1].toFloatOrNull() ?: 0f
                sysEntries.add(Entry(index.toFloat(), sys))
                diaEntries.add(Entry(index.toFloat(), dia))
            }
        }

        val hrDataSet = LineDataSet(hrEntries, "Heart Rate").apply {
            color = Color.BLUE
            setCircleColor(Color.BLUE)
            lineWidth = 3f
            circleRadius = 6f
            setDrawValues(true)
            valueTextSize = 14f
        }
        
        val sysDataSet = LineDataSet(sysEntries, "Systolic BP").apply {
            color = Color.RED
            setCircleColor(Color.RED)
            lineWidth = 3f
            circleRadius = 6f
            setDrawValues(true)
            valueTextSize = 14f
        }

        val diaDataSet = LineDataSet(diaEntries, "Diastolic BP").apply {
            color = Color.GREEN
            setCircleColor(Color.GREEN)
            lineWidth = 3f
            circleRadius = 6f
            setDrawValues(true)
            valueTextSize = 14f
        }

        binding.vitalChart.apply {
            data = LineData(hrDataSet, sysDataSet, diaDataSet)
            description.text = "Last 7 entries"
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val idx = value.toInt()
                    return if (idx >= 0 && idx < dates.size) dates[idx] else ""
                }
            }
            xAxis.granularity = 1f
            animateX(1000)
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
