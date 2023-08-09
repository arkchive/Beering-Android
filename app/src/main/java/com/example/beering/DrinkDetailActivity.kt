package com.example.beering

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beering.api.DrinkDetailApiService
import com.example.beering.api.DrinkDetailResponse
import com.example.beering.api.getRetrofit_sync
import com.example.beering.databinding.ActivityDrinkDetailBinding
import com.example.naverwebtoon.data.DrinkDetail
import retrofit2.Call
import retrofit2.Response

class DrinkDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityDrinkDetailBinding

    var isInterest = false

    var reviewAdapter: ReviewAdapter? = null
    var reviewList = ArrayList<DrinkDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrinkDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 데이터를 받아서 처리
        val drinkId = intent.getStringExtra("drinkId")?.toInt()
        Log.d("drinkId", drinkId.toString())

        //api연결

        if (drinkId != null) {
            setDrinkDetail(drinkId)
        }

        initReviewData()

        reviewAdapter = ReviewAdapter(reviewList)
        binding.reviewRv.adapter = reviewAdapter
        binding.reviewRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.buttonInterest.setOnClickListener {
            isInterest = !isInterest
            updateInterest(isInterest)
        }


        binding.drinkDetailReviewWritingBtn.setOnClickListener {
            val intent = Intent(this, ReviewWritingActivity::class.java)
            startActivity(intent)
        }

        binding.reviewMoreIv.setOnClickListener {
            val intent = Intent(this, DrinkDetailReviewsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setDrinkDetail(drinkId : Int) {
        val drinkDetailService = getRetrofit_sync().create(DrinkDetailApiService::class.java)
        drinkDetailService.getDrinkDetail(drinkId).enqueue(object : retrofit2.Callback<DrinkDetailResponse>{
            override fun onResponse(
                call: Call<DrinkDetailResponse>,
                response: Response<DrinkDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()
                    Log.i("DRINKDETAIL/SUCCESS", resp.toString())

                    val nameKr = resp!!.result.nameKr
                    binding.mainNameTv.text = nameKr
                    val totalRating = resp!!.result.totalRating
                    binding.mainNameTv.text = totalRating.toString()
                    val reviewCount = resp!!.result.reviewCount
                    binding.drinkDetailReviewCountTv.text = reviewCount.toString()
                    val alcohol = resp!!.result.alcohol
                    binding.alcoholPercentageTv.text = alcohol.toString()
                    val description = resp!!.result.description
                    binding.detailInformationTv.text = description
                    val manufacturer = resp!!.result.manufacturer
                    binding.beerCategory.text = manufacturer
                }
            }
            override fun onFailure(call: Call<DrinkDetailResponse>, t: Throwable) {
                Log.i("DRINKDETAIL/FAILURE", t.message.toString())
            }

            })

        }

    private fun initReviewData() {

//        reviewList = arrayListOf(
//            DrinkDetail(
//                "하이네켄",
//                R.drawable.login_request_banner,
//                "이 맥주는 정말 맛있고 아주 달콤합니다. 이 맥주는 놀랍게도 수입맥주가 아니라 !!!!! 우리나라 조선시대에 만들어진 맥주입니돳 블라블라",
//                4.5f,
//                100,
//                4.7f
//            ),
//            DrinkDetail(
//                "하이두켄",
//                R.drawable.login_request_banner,
//                "이 맥주는 아주ㅅ 씁니닷 이 맥주는 아주ㅅ 씁니닷 이 맥주는 아주ㅅ 씁니닷 이 맥주는 아주ㅅ 씁니닷 이 맥주는 아주ㅅ 씁니닷 이 맥주는 아주ㅅ 씁니닷 이 맥주는 아주ㅅ 씁니닷 이 맥주는 아주ㅅ 씁니닷 이 맥주는 아주ㅅ 씁니닷",
//                4.5f,
//                105,
//                4.7f
//            ),
//            DrinkDetail(
//                "하이세켄",
//                R.drawable.login_request_banner,
//                "이 맥주는 딱히 설명할 것이 없네유ㅠㅠ",
//                4.5f,
//                200,
//                4.7f
//            )
//        )


    }

    fun updateInterest(state : Boolean) {
        if (state) { // 찜하기 on 상태
            binding.interestOn.visibility= View.VISIBLE
            binding.interestOff.visibility= View.GONE
        }else {
            binding.interestOn.visibility= View.GONE
            binding.interestOff.visibility= View.VISIBLE
        }
    }


}
