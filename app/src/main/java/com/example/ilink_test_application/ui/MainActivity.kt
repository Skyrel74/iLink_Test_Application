package com.example.ilink_test_application.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ilink_test_application.R
import com.example.ilink_test_application.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Class of main access to user
 *
 * @property[binding] provides layout ids
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    /**
     * Function to action when [Activity] is created
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setBottomNavigation()
    }

    /**
     * Function to set [NavController] into bottom navigation
     */
    private fun setBottomNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        val navController = navHost.navController
        binding.bottomNavBar.setupWithNavController(navController)
    }
}
