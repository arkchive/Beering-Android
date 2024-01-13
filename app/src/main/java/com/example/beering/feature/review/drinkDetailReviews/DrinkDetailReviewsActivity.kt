package com.example.beering.feature.review.drinkDetailReviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beering.util.getRetrofit_header
import com.example.beering.util.getAccessToken
import com.example.beering.databinding.ActivityDrinkDetailReviewsBinding
import com.example.beering.feature.home.ReviewsApiService
import com.example.beering.feature.home.ReviewsResponse
import retrofit2.Call
import retrofit2.Response

class DrinkDetailReviewsActivity : AppCompatActivity() {
    lateinit var binding : ActivityDrinkDetailReviewsBinding
    lateinit var drinkDetailReviewsAdapter: DrinkDetailReviewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrinkDetailReviewsBinding.inflate(layoutInflater)
        val recyclerView: RecyclerView = binding.myReviewsPostRv
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            finish()

            val fragmentManager: FragmentManager = supportFragmentManager
            fragmentManager.popBackStack()
        }

        val drinkDetailReviewsService = getRetrofit_header(getAccessToken(this).toString()).create(
            ReviewsApiService::class.java)
        drinkDetailReviewsService.getReviews().enqueue(object: retrofit2.Callback<ReviewsResponse>{
            override fun onResponse(
                call: Call<ReviewsResponse>,
                response: Response<ReviewsResponse>
            ) {
                val resp = response.body()
                if (resp!!.isSuccess) {
                    val reviews = resp.result.content
                    drinkDetailReviewsAdapter = DrinkDetailReviewsAdapter(reviews)
                    recyclerView.adapter = drinkDetailReviewsAdapter
                    recyclerView.layoutManager = LinearLayoutManager(this@DrinkDetailReviewsActivity)
                } else {

                }
            }
            override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}