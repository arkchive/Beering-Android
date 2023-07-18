package com.example.beering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.beering.databinding.FragmentMyBinding

class MyFragment : Fragment() {
    lateinit var binding: FragmentMyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root


        // 내 리뷰 모아보기 설정


        // api 연결설정



    }
}