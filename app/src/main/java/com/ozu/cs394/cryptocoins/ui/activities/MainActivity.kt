package com.ozu.cs394.cryptocoins.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ozu.cs394.cryptocoins.R
import com.ozu.cs394.cryptocoins.ui.adapter.RecyclerAdapter
import kotlinx.android.synthetic.main.home_fragment.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CryptoCoins)
        supportFragmentManager.findFragmentById(R.id.nav_graph)
        setContentView(R.layout.activity_main)

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}