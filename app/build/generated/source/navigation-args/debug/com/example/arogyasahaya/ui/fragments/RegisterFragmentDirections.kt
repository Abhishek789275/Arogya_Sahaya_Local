package com.example.arogyasahaya.ui.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.arogyasahaya.R

public class RegisterFragmentDirections private constructor() {
  public companion object {
    public fun actionRegisterFragmentToLoginFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_registerFragment_to_loginFragment)
  }
}
