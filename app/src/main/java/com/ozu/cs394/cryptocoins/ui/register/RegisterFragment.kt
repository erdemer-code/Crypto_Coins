package com.ozu.cs394.cryptocoins.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ozu.cs394.cryptocoins.R
import com.ozu.cs394.cryptocoins.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment() {
    private var _binding: RegisterFragmentBinding? = null

    private val binding get() = _binding!!
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            findNavController().navigate(R.id.action_registerFragment_to_onBoardingFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}