package com.example.beering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.beering.databinding.DialogTerm3Binding

class Term3Dialog: DialogFragment() {
    lateinit var binding: DialogTerm3Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogTerm3Binding.inflate(inflater, container, false)

        binding.termsButtonCancelBt.setOnClickListener{
            dismiss()
        }


        return binding.root
    }

}