package com.example.beering.feature.auth.join.term

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.beering.databinding.DialogTerm1Binding

class Term1Dialog: DialogFragment() {
    lateinit var binding: DialogTerm1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogTerm1Binding.inflate(inflater, container, false)

        binding.termsButtonCancelBt.setOnClickListener{
            dismiss()
        }


        return binding.root
    }

}