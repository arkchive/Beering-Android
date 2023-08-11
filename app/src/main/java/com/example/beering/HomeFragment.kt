package com.example.beering
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beering.api.ReviewsApiService
import com.example.beering.api.ReviewsResponse
import com.example.beering.api.getRetrofit_header
import com.example.beering.data.getAccessToken
import com.example.beering.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Response

class HomeFragment: Fragment() {
    lateinit var binding:FragmentHomeBinding
    lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val recyclerView: RecyclerView = binding.homePostRv

        // api 연결
        val homeService =
            getRetrofit_header(getAccessToken(requireContext()).toString()).create(ReviewsApiService::class.java)
        homeService.getReviews().enqueue(object : retrofit2.Callback<ReviewsResponse>{
            override fun onResponse(
                call: Call<ReviewsResponse>, response: Response<ReviewsResponse>
            ){
                val resp = response.body()
                if(resp!!.isSuccess) {
                    val reviews = resp.result.content
                    homeAdapter = HomeAdapter(reviews)
                    recyclerView.adapter = homeAdapter
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                }else {

                }
            }

            override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })


        return binding.root
    }

}