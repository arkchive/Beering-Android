package com.example.beering.feature.auth.join.term

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.beering.databinding.DialogTerm2Binding

class Term2Dialog: DialogFragment() {
    lateinit var binding: DialogTerm2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogTerm2Binding.inflate(inflater, container, false)

        binding.termsButtonCancelBt.setOnClickListener{
            dismiss()
        }


        return binding.root
    }

}