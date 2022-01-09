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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozu.cs394.cryptocoins.R
import com.ozu.cs394.cryptocoins.databinding.LoginFragmentBinding
import com.ozu.cs394.cryptocoins.ui.activities.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class LoginFragment : Fragment() {
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
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
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.btnLoginScreen.setOnClickListener {
            validateLogin(binding.etEmail.text.toString(),binding.etPassword.text.toString())
        }
        binding.twCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    private fun validateLogin(email: String,password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {

                    Toast.makeText(requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun checkFirebase(email:String,password:String) {


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

