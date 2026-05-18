package com.example.arogyasahaya.ui.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.arogyasahaya.R

public class LoginFragmentDirections private constructor() {
  public companion object {
    public fun actionLoginFragmentToRegisterFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_loginFragment_to_registerFragment)

    public fun actionLoginFragmentToDashboardFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_loginFragment_to_dashboardFragment)
  }
}
