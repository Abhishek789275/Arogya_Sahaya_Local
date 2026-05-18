package com.example.arogyasahaya.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.arogyasahaya.R
import com.example.arogyasahaya.databinding.FragmentLoginBinding
import com.example.arogyasahaya.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val contact = binding.etLoginContact.text.toString().trim()
            val password = binding.etLoginPassword.text.toString()

            if (contact.isNotEmpty() && password.isNotEmpty()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    val user = viewModel.login(contact, password)
                    if (user != null) {
                        viewModel.updateLoginStatus(user.id, true)
                        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                    } else {
                        Toast.makeText(context, "Invalid Email/Phone or Password", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvGoToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
