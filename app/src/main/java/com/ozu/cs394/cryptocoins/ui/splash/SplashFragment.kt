package com.ozu.cs394.cryptocoins.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ozu.cs394.cryptocoins.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        waitAndGo()
    }

    private fun waitAndGo() {
        lifecycleScope.launch {
            delay(3000)
            withContext(Dispatchers.Main) {
                    findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
                }
            }
        }
    }

