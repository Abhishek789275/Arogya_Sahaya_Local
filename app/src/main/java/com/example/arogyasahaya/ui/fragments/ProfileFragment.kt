package com.example.arogyasahaya.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.arogyasahaya.R
import com.example.arogyasahaya.data.entities.User
import com.example.arogyasahaya.databinding.FragmentProfileBinding
import com.example.arogyasahaya.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.util.Locale

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    private val languages = listOf("English", "Kannada", "Hindi", "Telugu", "Malayalam")
    private val languageCodes = listOf("en", "kn", "hi", "te", "ml")
    
    private var isEditMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLanguageSpinner()

        viewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                if (!isEditMode) {
                    binding.etName.setText(it.username) 
                    binding.etAge.setText(it.age.toString())
                    binding.etConditions.setText(it.chronicConditions)
                    binding.etEmergencyContact.setText(it.emergencyContact)
                    binding.etSosNumber.setText(it.sosNumber)
                    binding.switchDarkMode.isChecked = it.isDarkMode
                    
                    val langIndex = languageCodes.indexOf(it.preferredLanguage)
                    if (langIndex >= 0) {
                        binding.spinnerLanguage.setSelection(langIndex)
                    }
                }
            }
        }

        binding.btnEditProfile.setOnClickListener {
            toggleEditMode(true)
        }

        binding.btnSaveProfile.setOnClickListener {
            saveProfile()
        }

        binding.btnLogout.setOnClickListener {
            logout()
        }
        
        toggleEditMode(false)
    }

    private fun toggleEditMode(enabled: Boolean) {
        isEditMode = enabled
        
        val fields = listOf(
            binding.etName, binding.etAge, binding.etConditions, 
            binding.etEmergencyContact, binding.etSosNumber
        )
        
        fields.forEach { field ->
            field.isFocusable = enabled
            field.isFocusableInTouchMode = enabled
            field.isCursorVisible = enabled
        }
        
        binding.spinnerLanguage.isEnabled = enabled
        binding.switchDarkMode.isEnabled = enabled
        
        binding.btnSaveProfile.visibility = if (enabled) View.VISIBLE else View.GONE
        binding.btnEditProfile.visibility = if (enabled) View.GONE else View.VISIBLE
    }

    private fun setupLanguageSpinner() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLanguage.adapter = adapter
    }

    private fun saveProfile() {
        val username = binding.etName.text.toString()
        val ageString = binding.etAge.text.toString()
        val selectedLangCode = languageCodes[binding.spinnerLanguage.selectedItemPosition]
        val isDarkMode = binding.switchDarkMode.isChecked

        if (username.isNotEmpty() && ageString.isNotEmpty()) {
            val currentUser = viewModel.user.value
            
            val updatedUser = User(
                id = currentUser?.id ?: 1,
                username = username,
                contact = currentUser?.contact ?: "",
                password = currentUser?.password ?: "",
                age = ageString.toIntOrNull() ?: 0, 
                chronicConditions = binding.etConditions.text.toString(), 
                emergencyContact = binding.etEmergencyContact.text.toString(),
                sosNumber = binding.etSosNumber.text.toString().ifEmpty { "108" },
                preferredLanguage = selectedLangCode,
                isDarkMode = isDarkMode,
                isLoggedIn = true
            )
            viewModel.insertUser(updatedUser)
            
            val sharedPref = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
            val oldLang = sharedPref.getString("lang", "en")
            val oldMode = sharedPref.getBoolean("dark_mode", false)

            sharedPref.edit().apply {
                putString("lang", selectedLangCode)
                putBoolean("dark_mode", isDarkMode)
                apply()
            }

            toggleEditMode(false)

            if (selectedLangCode != oldLang || isDarkMode != oldMode) {
                AppCompatDelegate.setDefaultNightMode(
                    if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
                )
                requireActivity().recreate()
            }

            Toast.makeText(context, "Changes Saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Please fill required fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun logout() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.user.value?.let {
                viewModel.updateLoginStatus(it.id, false)
            }
            findNavController().navigate(R.id.loginFragment)
            Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
