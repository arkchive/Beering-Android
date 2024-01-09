package com.example.beering.feature.my

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.beering.feature.my.drinkFavorite.DrinkFavoriteActivity
import com.example.beering.feature.my.likeReviews.LikeReviewsActivity
import com.example.beering.feature.my.myReviews.MyReviewsActivity
import com.example.beering.R
import com.example.beering.util.token.token
import com.example.beering.databinding.FragmentMyBinding
import com.example.beering.util.getAccessToken
import com.example.beering.util.getRetrofit_header
import com.example.beering.util.stateLogin
import retrofit2.Call
import retrofit2.Response

class MyFragment : Fragment() {
    lateinit var binding: FragmentMyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyBinding.inflate(inflater, container, false)


        // 로그인 상태에 따른 화면 설정
        if (stateLogin(requireContext())) {
            // 로그인 상태일때
            binding.myMyReviewsTv.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.myMyReviewsButtonIv.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.myMyReviewsButtonIv.isEnabled = true
            binding.myMyReviewsUnderlineV.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )

            binding.myFavoriteDrinkTv.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.myFavoriteDrinkButtonIv.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.myFavoriteDrinkButtonIv.isEnabled = true
            binding.myFavoriteDrinkUnderlineV.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )

            binding.myFavoriteReviewTv.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.myFavoriteReviewButtonIv.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.myFavoriteReviewButtonIv.isEnabled = true
            binding.myFavoriteReviewUnderlineV.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )

            // api 연결설정
            val myService =
                getRetrofit_header(getAccessToken(requireContext()).toString()).create(MyApiService::class.java)
            myService.getMy().enqueue(object : retrofit2.Callback<MyResponse> {
                override fun onResponse(
                    call: Call<MyResponse>,
                    response: Response<MyResponse>
                ) {
                    if(response.isSuccessful) {
                        val resp = response.body()
                        if (resp!!.isSuccess) {
                            binding.myProfileNicknameTv.text = resp!!.result.nickname
                            binding.myProfileEmailTv.text = resp!!.result.username
                            val defaultImage = R.drawable.img_default_profile
                            val url = resp!!.result.url

                            Glide.with(this@MyFragment)
                                .load(url) // 불러올 이미지 url
                                .placeholder(defaultImage) // 이미지 로딩 시작하기 전 표시할 이미지
                                .error(defaultImage) // 로딩 에러 발생 시 표시할 이미지
                                .fallback(defaultImage) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                                .circleCrop() // 동그랗게 자르기
                                .into(binding.myProfileImgIv)


                        } else {
                            if (resp.responseCode == 2003) token(requireContext())

                        }
                    }
                }

                override fun onFailure(call: Call<MyResponse>, t: Throwable) {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("요청 오류")
                    builder.setMessage("서버에 요청을 실패하였습니다.")
                    builder.setPositiveButton("네") { dialog, which ->
                        dialog.dismiss()
                    }
                    val dialog = builder.create()
                    dialog.show()
                }

            })
            


        } else {
            // 비로그인 상태일때
            binding.myMyReviewsTv.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray01
                )
            )
            binding.myMyReviewsButtonIv.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray01
                )
            )
            binding.myMyReviewsButtonIv.isEnabled = false
            binding.myMyReviewsUnderlineV.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray01
                )
            )



            binding.myFavoriteDrinkTv.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray01
                )
            )
            binding.myFavoriteDrinkButtonIv.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray01
                )
            )
            binding.myFavoriteDrinkButtonIv.isEnabled = false
            binding.myFavoriteDrinkUnderlineV.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray01
                )
            )

            binding.myFavoriteReviewTv.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray01
                )
            )
            binding.myFavoriteReviewButtonIv.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray01
                )
            )
            binding.myFavoriteReviewButtonIv.isEnabled = false
            binding.myFavoriteReviewUnderlineV.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray01
                )
            )
        }

        binding.myMyReviewsButtonIv.setOnClickListener {
            val intent = Intent(requireContext(), MyReviewsActivity::class.java)
            startActivity(intent)
        }

        binding.myFavoriteDrinkButtonIv.setOnClickListener {
            val intent = Intent(requireContext(), DrinkFavoriteActivity::class.java)
            startActivity(intent)
        }

        binding.myFavoriteReviewButtonIv.setOnClickListener {
            val intent = Intent(requireContext(), LikeReviewsActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}