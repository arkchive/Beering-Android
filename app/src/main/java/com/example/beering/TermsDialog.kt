package com.example.beering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.beering.databinding.DialogTermsBinding

class TermsDialog: DialogFragment() {
    lateinit var binding: DialogTermsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogTermsBinding.inflate(inflater, container, false)

        binding.termsButtonCancelBt.setOnClickListener{
            dismiss()
        }


        return binding.root
    }

}