package com.example.todoaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHost: NavHostFragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment

        setupActionBarWithNavController(navController = navHost.navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHost)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}