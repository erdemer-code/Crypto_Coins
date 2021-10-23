package com.ozu.cs394.cryptocoins.ui.on_boarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ozu.cs394.cryptocoins.R
import com.ozu.cs394.cryptocoins.databinding.FragmentOnBoardingBinding


class OnBoardingFragment : Fragment() {
    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)

        binding.apply {
            btnLogIn.setOnClickListener {
                findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
            }
            btnSignIn.setOnClickListener {
                findNavController().navigate(R.id.action_onBoardingFragment_to_registerFragment)
            }
        }
        return (binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}