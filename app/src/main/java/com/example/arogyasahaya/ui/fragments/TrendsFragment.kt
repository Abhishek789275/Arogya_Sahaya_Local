package com.example.arogyasahaya.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.arogyasahaya.R
import com.example.arogyasahaya.data.entities.Vitals
import com.example.arogyasahaya.databinding.FragmentTrendsBinding
import com.example.arogyasahaya.ui.viewmodel.MainViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TrendsFragment : Fragment() {

    private var _binding: FragmentTrendsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.recentVitals.observe(viewLifecycleOwner) { vitalsList ->
            setupChart(vitalsList)
        }
    }

    private fun setupChart(vitalsList: List<Vitals>) {
        if (vitalsList.isEmpty()) return

        val hrEntries = mutableListOf<Entry>()
        val sysEntries = mutableListOf<Entry>()
        val diaEntries = mutableListOf<Entry>()
        val dates = mutableListOf<String>()
        val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
        
        // Use last 7 entries in chronological order
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

        val primaryBlue = ContextCompat.getColor(requireContext(), R.color.primary_blue)
        val textColor = ContextCompat.getColor(requireContext(), R.color.text_color)
        val errorRed = ContextCompat.getColor(requireContext(), R.color.emergency_red)
        val successGreen = ContextCompat.getColor(requireContext(), R.color.success_green)

        val hrDataSet = LineDataSet(hrEntries, getString(R.string.hr_hint)).apply {
            color = primaryBlue
            setCircleColor(primaryBlue)
            lineWidth = 4f
            circleRadius = 8f
            setDrawValues(true)
            valueTextSize = 14f
            valueTextColor = textColor
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }
        
        val sysDataSet = LineDataSet(sysEntries, "Systolic BP").apply {
            color = errorRed
            setCircleColor(errorRed)
            lineWidth = 3f
            circleRadius = 6f
            setDrawValues(true)
            valueTextSize = 14f
            valueTextColor = textColor
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }
        
        val diaDataSet = LineDataSet(diaEntries, "Diastolic BP").apply {
            color = successGreen
            setCircleColor(successGreen)
            lineWidth = 3f
            circleRadius = 6f
            setDrawValues(true)
            valueTextSize = 14f
            valueTextColor = textColor
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        binding.trendChart.apply {
            data = LineData(hrDataSet, sysDataSet, diaDataSet)
            description.isEnabled = false
            
            // Text colors for axes
            xAxis.textColor = textColor
            axisLeft.textColor = textColor
            axisRight.isEnabled = false
            legend.textColor = textColor
            
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val idx = value.toInt()
                    return if (idx >= 0 && idx < dates.size) dates[idx] else ""
                }
            }
            xAxis.granularity = 1f
            axisLeft.setDrawGridLines(true)
            
            setTouchEnabled(true)
            setPinchZoom(true)
            animateX(1000)
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
