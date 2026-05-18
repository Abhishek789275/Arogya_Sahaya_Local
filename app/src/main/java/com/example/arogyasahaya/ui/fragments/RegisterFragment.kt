package com.example.arogyasahaya.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.arogyasahaya.R
import com.example.arogyasahaya.data.entities.User
import com.example.arogyasahaya.databinding.FragmentRegisterBinding
import com.example.arogyasahaya.ui.viewmodel.MainViewModel
import java.util.regex.Pattern

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val username = binding.etRegUsername.text.toString().trim()
            val contact = binding.etRegContact.text.toString().trim()
            val password = binding.etRegPassword.text.toString()

            if (username.isNotEmpty() && contact.isNotEmpty() && password.isNotEmpty()) {
                if (!isValidContact(contact)) {
                    binding.tilRegContact?.error = "Enter a valid email or phone number"
                    return@setOnClickListener
                } else {
                    binding.tilRegContact?.error = null
                }

                if (isValidPassword(password)) {
                    val newUser = User(
                        username = username,
                        contact = contact,
                        password = password
                    )
                    viewModel.insertUser(newUser)
                    Toast.makeText(context, "Registration Successful!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                } else {
                    binding.tilRegPassword.error = "Password must contain letters, numbers and a special character"
                }
            } else {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvGoToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun isValidContact(contact: String): Boolean {
        val isEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(contact).matches()
        val isPhone = android.util.Patterns.PHONE.matcher(contact).matches() && contact.length >= 10
        return isEmail || isPhone
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$"
        val pattern = Pattern.compile(passwordPattern)
        return pattern.matcher(password).matches()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
