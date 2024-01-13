package com.example.beering.feature.my.myReviews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beering.feature.home.ReviewsApiService
import com.example.beering.feature.home.ReviewsResponse
import com.example.beering.util.getRetrofit_header
import com.example.beering.util.getAccessToken
import com.example.beering.databinding.ActivityMyReviewsBinding
import retrofit2.Call
import retrofit2.Response

class MyReviewsActivity : AppCompatActivity() {
    lateinit var binding : ActivityMyReviewsBinding
    lateinit var myreviewsAdapter : MyReviewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReviewsBinding.inflate(layoutInflater)
        val recyclerView: RecyclerView = binding.myReviewsPostRv
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val myReviewsService = getRetrofit_header(getAccessToken(this).toString()).create(
            ReviewsApiService::class.java)
        myReviewsService.getReviews().enqueue(object: retrofit2.Callback<ReviewsResponse>{
            override fun onResponse(
                call: Call<ReviewsResponse>,
                response: Response<ReviewsResponse>
            ) {
                val resp = response.body()
                if (resp!!.isSuccess) {
                    val reviews = resp.result.content
                    myreviewsAdapter = MyReviewsAdapter(reviews)
                    recyclerView.adapter = myreviewsAdapter
                    recyclerView.layoutManager = LinearLayoutManager(this@MyReviewsActivity)
                } else {

                }
            }

            override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

}

