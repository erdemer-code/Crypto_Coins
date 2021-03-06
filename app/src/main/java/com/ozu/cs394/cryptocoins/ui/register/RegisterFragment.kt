package com.ozu.cs394.cryptocoins.ui.register

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ozu.cs394.cryptocoins.R
import com.ozu.cs394.cryptocoins.databinding.RegisterFragmentBinding
import android.widget.CheckBox
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozu.cs394.cryptocoins.ui.activities.MainActivity
import com.ozu.cs394.cryptocoins.ui.dialogs.RegisterConfirmDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_fragment.*



class RegisterFragment : Fragment() {
    private var _binding: RegisterFragmentBinding? = null

    private val binding get() = _binding!!
    private lateinit var viewModel: RegisterViewModel
    private lateinit var auth: FirebaseAuth

    private var checkBoxTicked: Boolean? = false

    companion object {
        var USERNAME = "USERNAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            findNavController().navigate(R.id.action_registerFragment_to_onBoardingFragment)
        }
        // Initialize Firebase Auth
        auth = Firebase.auth



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
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding.apply {
            textView2.movementMethod = LinkMovementMethod.getInstance();
            checkBox.setOnClickListener(View.OnClickListener {
                checkBoxTicked = checkBox.isChecked
            })
            twHaveAnAccount.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }

            btnRegisterPage.setOnClickListener {
                validateRegister()
            }
        }

    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    RegisterConfirmDialogFragment().show(childFragmentManager, RegisterConfirmDialogFragment.TAG)
                } else {
                    Toast.makeText(requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun validateRegister() {
        if (checkBoxTicked == true && binding.etPasswordRegister.text.toString().equals(binding.etPasswordRegisterConfirm.text.toString()) && binding.etPasswordRegister.text.toString() != "" && binding.etPasswordRegisterConfirm.text.toString() != ""){
            USERNAME = binding.etUsername.text.toString()
            createAccount(binding.etRegisterEmail.text.toString(),binding.etPasswordRegister.text.toString())
        }
        else if (checkBoxTicked == false)
            Toast.makeText(requireContext(),"Please confirm the user agreement.",Toast.LENGTH_SHORT).show()
        else if (binding.etPasswordRegister.text.toString() != binding.etPasswordRegisterConfirm.text.toString())
            Toast.makeText(requireContext(),"Passwords does not match.",Toast.LENGTH_SHORT).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}