package com.example.arogyasahaya.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.activity.result.contract.ActivityResultContracts
import android.telephony.SmsManager
import com.example.arogyasahaya.R
import com.example.arogyasahaya.databinding.FragmentDashboardBinding
import com.example.arogyasahaya.ui.viewmodel.MainViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private var sosNumber: String = "108"

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val callGranted = permissions[Manifest.permission.CALL_PHONE] ?: false
            val smsGranted = permissions[Manifest.permission.SEND_SMS] ?: false

            if (callGranted && smsGranted) {
                executeSOS()
            } else {
                Toast.makeText(context, "Permissions required for full SOS features", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnProfileLogo.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        binding.btnSOS.setOnClickListener {
            triggerSOS()
        }

        // Navigate to separate ASHA Calendar screen
        binding.btnOpenAshaCalendar.setOnClickListener {
            findNavController().navigate(R.id.ashaCalendarFragment)
        }

        // Observe user for Greeting and SOS number
        viewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                sosNumber = it.sosNumber
                binding.tvGreeting.text = getString(R.string.greeting_format, it.username)
            }
        }

        // Observe medications to show the next one and adherence
        viewModel.allMedications.observe(viewLifecycleOwner) { medications ->
            if (medications.isNotEmpty()) {
                val nextMed = medications.first()
                binding.tvNextMedicine.text = getString(R.string.med_display_format, nextMed.medicineName, nextMed.timeOfDay)
                
                val totalCount = medications.size
                val takenCount = medications.count { it.isTaken }
                val percentage = if (totalCount > 0) (takenCount.toFloat() / totalCount * 100).toInt() else 0
                
                binding.tvAdherence.text = getString(R.string.adherence_format, takenCount, totalCount, percentage)
                binding.pbAdherence.progress = percentage
            } else {
                binding.tvNextMedicine.text = getString(R.string.no_medicine_scheduled)
                binding.tvAdherence.text = getString(R.string.adherence_format, 0, 0, 0)
                binding.pbAdherence.progress = 0
            }
        }
    }

    private fun triggerSOS() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            executeSOS()
        } else {
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS))
        }
    }

    private fun executeSOS() {
        Toast.makeText(requireContext(), getString(R.string.sos_triggered_format, sosNumber), Toast.LENGTH_LONG).show()
        
        try {
            val smsManager = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                requireContext().getSystemService(SmsManager::class.java)
            } else {
                @Suppress("DEPRECATION")
                SmsManager.getDefault()
            }
            smsManager?.sendTextMessage(sosNumber, null, getString(R.string.sos_sms_message), null, null)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), getString(R.string.sms_failed), Toast.LENGTH_SHORT).show()
        }

        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$sosNumber")
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
