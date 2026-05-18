package com.example.arogyasahaya.ui.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.arogyasahaya.R

public class SplashFragmentDirections private constructor() {
  public companion object {
    public fun actionSplashFragmentToLoginFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_splashFragment_to_loginFragment)

    public fun actionSplashFragmentToDashboardFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_splashFragment_to_dashboardFragment)
  }
}
