package com.example.beering.feature.my.likeReviews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beering.feature.home.ReviewsApiService
import com.example.beering.feature.home.ReviewsResponse
import com.example.beering.util.getRetrofit_header
import com.example.beering.util.getAccessToken
import com.example.beering.databinding.ActivityLikeReviewsBinding
import com.example.beering.util.BaseActivity
import retrofit2.Call
import retrofit2.Response

class LikeReviewsActivity: BaseActivity<ActivityLikeReviewsBinding>(ActivityLikeReviewsBinding::inflate) {
    lateinit var likeReviewsAdapter : LikeReviewsAdapter

    override fun initAfterBinding() {
        val recyclerView: RecyclerView = binding.likeReviewsPostRv

        binding.btnBack.setOnClickListener {
            finish()
        }

        val likeReviewsService = getRetrofit_header(getAccessToken(this).toString()).create(
            ReviewsApiService::class.java)
        likeReviewsService.getReviews().enqueue(object: retrofit2.Callback<ReviewsResponse>{
            override fun onResponse(
                call: Call<ReviewsResponse>,
                response: Response<ReviewsResponse>
            ) {
                val resp = response.body()
                if (resp!!.isSuccess) {
                    val reviews = resp.result.content
                    likeReviewsAdapter = LikeReviewsAdapter(reviews)
                    recyclerView.adapter = likeReviewsAdapter
                    recyclerView.layoutManager = LinearLayoutManager(this@LikeReviewsActivity)
                } else {

                }
            }

            override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

}