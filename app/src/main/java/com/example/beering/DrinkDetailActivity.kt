package com.example.beering

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beering.api.DrinkDetailApiService
import com.example.beering.api.DrinkDetailResponse
import com.example.beering.api.ReviewPreview
import com.example.beering.api.getRetrofit_sync
import com.example.beering.databinding.ActivityDrinkDetailBinding
import retrofit2.Call
import retrofit2.Response

class DrinkDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityDrinkDetailBinding

    var reviewPreviews : List<ReviewPreview>? = null

    var isInterest = false

    var reviewAdapter: ReviewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrinkDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 데이터를 받아서 처리
        val intent = intent
        var drinkId : Int? = null

        if (intent != null) {
            drinkId = intent.getIntExtra("drinkId", 1)
            Log.d("drinkId", drinkId.toString())

            //api연결
            if (drinkId != null) {
                setDrinkDetail(drinkId)
            }
        }

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
                    Log.i("GETDRINKDETAIL/SUCCESS", resp.toString())

                    val nameKr = resp!!.result.nameKr
                    binding.mainNameTv.text = nameKr
                    val totalRating = resp.result.totalRating
                    binding.mainNameTv.text = totalRating.toString()
                    val reviewCount = resp.result.reviewCount
                    binding.drinkDetailReviewCountTv.text = reviewCount.toString()
                    val alcohol = resp.result.alcohol
                    binding.alcoholPercentageTv.text = alcohol.toString()
                    val description = resp.result.description
                    binding.detailInformationTv.text = description
                    val manufacturer = resp.result.manufacturer
                    binding.beerCategory.text = manufacturer

                    //리뷰
                    reviewPreviews = resp.result.reviewPreviews
                    reviewAdapter = ReviewAdapter(reviewPreviews!!)
                    binding.reviewRv.adapter = reviewAdapter
                    binding.reviewRv.layoutManager = LinearLayoutManager(this@DrinkDetailActivity, LinearLayoutManager.HORIZONTAL, false)

                }
            }
            override fun onFailure(call: Call<DrinkDetailResponse>, t: Throwable) {
                Log.i("DRINKDETAIL/FAILURE", t.message.toString())
            }

            })

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
