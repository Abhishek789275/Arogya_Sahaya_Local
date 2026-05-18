package com.example.arogyasahaya

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import com.example.arogyasahaya.R
import com.example.arogyasahaya.databinding.ActivityMainBinding
import com.example.arogyasahaya.ui.viewmodel.MainViewModel
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply theme from preferences before onCreate
        val sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean("dark_mode", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Custom Navigation Logic for 6 items
        binding.navHome.setOnClickListener { navController.navigate(R.id.dashboardFragment) }
        binding.navReminder.setOnClickListener { navController.navigate(R.id.medicationReminderFragment) }
        binding.navManagement.setOnClickListener { navController.navigate(R.id.medicationScheduleFragment) }
        binding.navVitals.setOnClickListener { navController.navigate(R.id.vitalLogFragment) }
        binding.navTrends.setOnClickListener { navController.navigate(R.id.trendsFragment) }
        binding.navProfile.setOnClickListener { navController.navigate(R.id.profileFragment) }

        // Show/Hide Custom Navigation based on current fragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment, R.id.loginFragment, R.id.registerFragment -> {
                    binding.bottomNavContainer.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavContainer.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val sharedPref = newBase.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val lang = sharedPref.getString("lang", "en") ?: "en"
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration(newBase.resources.configuration)
        config.setLocale(locale)
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }
}
