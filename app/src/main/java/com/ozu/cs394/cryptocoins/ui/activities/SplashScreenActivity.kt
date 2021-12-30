package com.ozu.cs394.cryptocoins.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ozu.cs394.cryptocoins.R
import android.content.Intent
import com.ozu.cs394.cryptocoins.util.SharedPreferenceManager


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        val spManager = SharedPreferenceManager
        spManager.init(this.applicationContext)
        finish()
    }
}