package com.example.beering

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.beering.data.stateLogin
import com.example.beering.databinding.FragmentMyBinding

class MyFragment : Fragment() {
    lateinit var binding: FragmentMyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyBinding.inflate(inflater, container, false)


        // 내 리뷰 모아보기 설정


        // api 연결설정

        // 로그인 상태에 따른 화면 설정
        if (stateLogin(requireContext()))
        {
            binding.myMyReviewsTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.myMyReviewsButtonIv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
            binding.myMyReviewsButtonIv.isEnabled = true
            binding.myMyReviewsUnderlineV.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))

            binding.myFavoriteDrinkTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.myFavoriteDrinkButtonIv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
            binding.myFavoriteDrinkButtonIv.isEnabled = true
            binding.myFavoriteDrinkUnderlineV.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))

            binding.myFavoriteReviewTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.myFavoriteReviewButtonIv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
            binding.myFavoriteReviewButtonIv.isEnabled = true
            binding.myFavoriteReviewUnderlineV.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))

        } else {
            binding.myMyReviewsTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray01))
            binding.myMyReviewsButtonIv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.gray01))
            binding.myMyReviewsButtonIv.isEnabled = false
            binding.myMyReviewsUnderlineV.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray01))



            binding.myFavoriteDrinkTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray01))
            binding.myFavoriteDrinkButtonIv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.gray01))
            binding.myFavoriteDrinkButtonIv.isEnabled = false
            binding.myFavoriteDrinkUnderlineV.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray01))

            binding.myFavoriteReviewTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray01))
            binding.myFavoriteReviewButtonIv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.gray01))
            binding.myFavoriteReviewButtonIv.isEnabled = false
            binding.myFavoriteReviewUnderlineV.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray01))
        }

        binding.myMyReviewsButtonIv.setOnClickListener {
            val intent = Intent(requireContext(), MyReviewsActivity::class.java)
            startActivity(intent)
        }

        binding.myFavoriteDrinkButtonIv.setOnClickListener {
            val intent = Intent(requireContext(), DrinkFavoriteActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}