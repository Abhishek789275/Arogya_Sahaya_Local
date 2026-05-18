package com.example.arogyasahaya.ui.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.arogyasahaya.R

public class DashboardFragmentDirections private constructor() {
  public companion object {
    public fun actionDashboardFragmentToVitalLogFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboardFragment_to_vitalLogFragment)

    public fun actionDashboardFragmentToAshaCalendarFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboardFragment_to_ashaCalendarFragment)
  }
}
