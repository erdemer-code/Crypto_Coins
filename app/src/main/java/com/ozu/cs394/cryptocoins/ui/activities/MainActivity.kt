package com.ozu.cs394.cryptocoins.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.ozu.cs394.cryptocoins.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.findFragmentById(R.id.nav_graph)
        setContentView(R.layout.activity_main)

    }


}