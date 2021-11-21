package com.ozu.cs394.cryptocoins.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.ozu.cs394.cryptocoins.R
import com.ozu.cs394.cryptocoins.ui.register.RegisterFragment

class RegisterConfirmDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Crypto Coins")
        builder.setMessage("Hello! "+ RegisterFragment.USERNAME + " \nYour account has been created.")
        builder.setPositiveButton("Continue", object: DialogInterface.OnClickListener {
            override fun onClick(dialog:DialogInterface, which:Int) {
                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
            }
        })
        builder.setNegativeButton("Cancel", object: DialogInterface.OnClickListener {
            override fun onClick(dialog:DialogInterface, which:Int) {
                dismiss()
            }
        })
        return builder.create()
    }
    companion object {
        const val TAG = "RegisterConfirmationDialog"
    }
}