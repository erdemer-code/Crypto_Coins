package com.ozu.cs394.cryptocoins.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ozu.cs394.cryptocoins.R
import com.ozu.cs394.cryptocoins.util.SharedPreferenceManager

class MainActivity : AppCompatActivity() {
    private val spManager = SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.findFragmentById(R.id.nav_graph)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavBar)
        val navController = findNavController(R.id.nav_fragment)

        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.e("DestinationId",destination.id.toString())
            if(destination.id == R.id.onBoardingFragment|| destination.id == R.id.registerFragment
                || destination.id == R.id.loginFragment || destination.id == R.id.coinDetailFragment) {

                bottomNavigationView.visibility = View.GONE
            } else {

                bottomNavigationView.visibility = View.VISIBLE
            }
        }

        if (spManager.getData<Boolean>("SAVED") == true)
            navController.navigate(R.id.action_onBoardingFragment_to_homeFragment)

    }



}