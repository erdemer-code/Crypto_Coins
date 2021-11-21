package com.ozu.cs394.cryptocoins.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ozu.cs394.cryptocoins.R
import com.ozu.cs394.cryptocoins.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel

    companion object {
        const val EMAIL = "test@ozu.edu.tr"
        const val PASSWORD = "123456"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_loginFragment_to_onBoardingFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnLoginScreen.setOnClickListener {
            validateLogin()
        }
        binding.twCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    private fun validateLogin() {
        if(binding.etEmail.text.toString() == EMAIL && binding.etPassword.text.toString() == PASSWORD) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        } else {

            Toast.makeText(requireContext(),"Your email address or password is incorrect.",Toast.LENGTH_SHORT).show()
            binding.etPassword.setText("")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

