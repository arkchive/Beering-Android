package com.example.beering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.beering.databinding.FragmentDrinkSearchBinding

class DrinkSearchFragmentFragment: Fragment() {
    lateinit var binding: FragmentDrinkSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrinkSearchBinding.inflate(inflater, container, false)
        return binding.root

    }
}