package com.example.beering

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.beering.api.*
import com.example.beering.data.getAccessToken
import com.example.beering.databinding.ActivityReviewDetailBinding
import retrofit2.Call
import retrofit2.Response

class ReviewDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityReviewDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBack.setOnClickListener{
            finish()

            val fragmentManager: FragmentManager = supportFragmentManager
            fragmentManager.popBackStack()
        }

        // 데이터를 받아서 처리
        val intent = intent
        var reviewID : Int? = null

        if (intent != null) {
            reviewID = intent.getIntExtra("reviewId", -1)
            Log.d("reviewId", reviewID.toString())

            //api연결
            if (reviewID != null) {

            }
        }

        // reviewId 받아오도록 수정해야 함.
        val reviewId : Int = 2
        val ReviewDetailService =
            getRetrofit_header(getAccessToken(this).toString()).create(ReviewDetailApiService::class.java)
        ReviewDetailService.getReviewDetail(reviewId).enqueue(object : retrofit2.Callback<ReviewDetailResponse>{
            override fun onResponse(
                call: Call<ReviewDetailResponse>,
                response: Response<ReviewDetailResponse>
            ) {
                if (response.isSuccessful) {

                    val resp = response.body()

                    Log.i("GETREVIEWDETAIL/SUCCESS", resp.toString())

//                    binding.itemHomePostNameTv.text = resp!!.result.nickName
                    binding.reviewDetailDateTv.text = resp!!.result.createAt
//                    binding.reviewDetailInformationTv.text = resp.result.content
                    binding.reviewDetailScoreNumTv.text = resp.result.totalRating.toString()
                    val reviewRating = resp!!.result.totalRating
                    updateReviewRating(reviewRating)
                    binding.reviewDetailScoreNumTv.text= reviewRating.toString()

                    //항목별 별점 적용
                    binding.reviewDetailOption1Tv.text = resp.result.selectedOptions[0].name // 옵션 1 name
                    binding.reviewDetailOption1ScoreTv.text = resp.result.selectedOptions[0].rating.toString() // 옵션 1 rating
                    updateRating_option1(resp.result.selectedOptions[0].rating)

                    binding.reviewDetailOption2Tv.text = resp.result.selectedOptions[1].name // 옵션 2 name
                    binding.reviewDetailOption2ScoreTv.text = resp.result.selectedOptions[1].rating.toString() // 옵션 2 rating
                    updateRating_option2(resp.result.selectedOptions[1].rating)

                    binding.reviewDetailOption3Tv.text = resp.result.selectedOptions[2].name // 옵션 3 name
                    binding.reviewDetailOption3ScoreTv.text = resp.result.selectedOptions[2].rating.toString() // 옵션 3 rating
                    updateRating_option3(resp.result.selectedOptions[2].rating)

                    binding.reviewDetailOption4Tv.text = resp.result.selectedOptions[3].name // 옵션 4 name
                    binding.reviewDetailOption4ScoreTv.text = resp.result.selectedOptions[3].rating.toString() // 옵션 4 rating
                    updateRating_option4(resp.result.selectedOptions[3].rating)

                    binding.reviewLikeTv.text = resp.result.like.toString()
                    binding.reviewUnlikeTv.text = resp.result.dislike.toString()

//                    val rating_1 = resp.result.selectedOptions[1].rating.toString()
//                    binding.reviewDetailTasteScoreTv.text = rating_1
//                    updateRating_incense(resp.result.selectedOptions[1].rating)
//
//                    val rating_2 = resp.result.selectedOptions[2].rating.toString()
//                    binding.reviewDetailTasteScoreTv.text = rating_2
//                    updateRating_color(resp.result.selectedOptions[2].rating)
//
//                    val rating_3 = resp.result.selectedOptions[3].rating.toString()
//                    binding.reviewDetailTasteScoreTv.text = rating_3
//                    updateRating_throat(resp.result.selectedOptions[3].rating)


//                    //리뷰 이미지
//                    val drinkImageUrl = resp.result.reivewImageUrls[0]
//                    Log.i("profilImage", drinkImageUrl)
//                    if(drinkImageUrl == null){
//                        binding.mainImageIv.setImageResource(R.drawable.img_home_post)
//                    }else{ // 프로필 이미지가 있을 경우
//                        Glide.with(this@ReviewDetailActivity)
//                            .load(drinkImageUrl)
//                            .placeholder(R.drawable.img_home_post)
//                            .error(R.drawable.img_home_post)
//                            .fallback(R.drawable.img_home_post)
//                            .into(binding.mainImageIv)
//                    }
//                    //프로필 이미지
//                    val profileImageUrl = resp.result.profileImage
//                    if(profileImageUrl == null){
//                        binding.reviewDetailProfileIv.setImageResource(R.drawable.img_default_profile)
//                    }else{ // 프로필 이미지가 있을 경우
//                        Glide.with(this@ReviewDetailActivity)
//                            .load(profileImageUrl)
//                            .placeholder(R.drawable.img_default_profile)
//                            .error(R.drawable.img_default_profile)
//                            .fallback(R.drawable.img_default_profile)
//                            .circleCrop()
//                            .into(binding.reviewDetailProfileIv)
//                    }

                }
            }
            override fun onFailure(call: Call<ReviewDetailResponse>, t: Throwable) {
                Log.i("GETREVIEWDETAIL/FAILURE", t.message.toString())
            }

        })


    }

    private fun updateReviewRating(rating : Float){
        if(rating == 0.0f){
            //
        } else if(rating > 0.0f && rating < 1.0f){
            binding.reviewStar1Half.visibility = View.VISIBLE
        }else if(rating == 1.0f || rating > 1.0f) {
            binding.reviewStar1Full.visibility = View.VISIBLE
        }
        // 누적
        if(rating > 1.0f && rating < 2.0f){
            binding.reviewStar2Half.visibility = View.VISIBLE
        }else if(rating == 2.0f || rating > 2.0f){
            binding.reviewStar2Full.visibility = View.VISIBLE
        }

        if(rating > 2.0f && rating < 3.0f){
            binding.reviewStar3Half.visibility = View.VISIBLE
        }else if(rating == 3.0f || rating > 3.0f){
            binding.reviewStar3Full.visibility = View.VISIBLE
        }

        if(rating > 3.0f && rating < 4.0f){
            binding.reviewStar4Half.visibility = View.VISIBLE
        }else if(rating == 4.0f || rating > 4.0f){
            binding.reviewStar4Full.visibility = View.VISIBLE
        }

        if(rating > 4.0f && rating < 5.0f){
            binding.reviewStar5Half.visibility = View.VISIBLE
        }else if(rating == 5.0f || rating > 5.0f){
            binding.reviewStar5Full.visibility = View.VISIBLE
        }
    }
    private fun updateRating_option1(rating : Float){
        if(rating == 0.0f){
            //
        } else if(rating > 0.0f && rating < 1.0f){
            binding.option1Star1Half.visibility = View.VISIBLE
        }else if(rating == 1.0f || rating > 1.0f) {
            binding.option1Star1Full.visibility = View.VISIBLE
        }
        // 누적
        if(rating > 1.0f && rating < 2.0f){
            binding.option1Star2Half.visibility = View.VISIBLE
        }else if(rating == 2.0f || rating > 2.0f){
            binding.option1Star2Full.visibility = View.VISIBLE
        }

        if(rating > 2.0f && rating < 3.0f){
            binding.option1Star3Half.visibility = View.VISIBLE
        }else if(rating == 3.0f || rating > 3.0f){
            binding.option1Star3Full.visibility = View.VISIBLE
        }

        if(rating > 3.0f && rating < 4.0f){
            binding.option1Star4Half.visibility = View.VISIBLE
        }else if(rating == 4.0f || rating > 4.0f){
            binding.option1Star4Full.visibility = View.VISIBLE
        }

        if(rating > 4.0f && rating < 5.0f){
            binding.option1Star5Half.visibility = View.VISIBLE
        }else if(rating == 5.0f || rating > 5.0f){
            binding.option1Star5Full.visibility = View.VISIBLE
        }
    }
    private fun updateRating_option2(rating : Float){
        if(rating == 0.0f){
            //
        } else if(rating > 0.0f && rating < 1.0f){
            binding.option2Star1Half.visibility = View.VISIBLE
        }else if(rating == 1.0f || rating > 1.0f) {
            binding.option2Star1Full.visibility = View.VISIBLE
        }
        // 누적
        if(rating > 1.0f && rating < 2.0f){
            binding.option2Star2Half.visibility = View.VISIBLE
        }else if(rating == 2.0f || rating > 2.0f){
            binding.option2Star2Full.visibility = View.VISIBLE
        }

        if(rating > 2.0f && rating < 3.0f){
            binding.option2Star3Half.visibility = View.VISIBLE
        }else if(rating == 3.0f || rating > 3.0f){
            binding.option2Star3Full.visibility = View.VISIBLE
        }

        if(rating > 3.0f && rating < 4.0f){
            binding.option2Star4Half.visibility = View.VISIBLE
        }else if(rating == 4.0f || rating > 4.0f){
            binding.option2Star4Full.visibility = View.VISIBLE
        }

        if(rating > 4.0f && rating < 5.0f){
            binding.option2Star5Half.visibility = View.VISIBLE
        }else if(rating == 5.0f || rating > 5.0f){
            binding.option2Star5Full.visibility = View.VISIBLE
        }
    }
    private fun updateRating_option3(rating : Float){
        if(rating == 0.0f){
            //
        } else if(rating > 0.0f && rating < 1.0f){
            binding.option3Star1Half.visibility = View.VISIBLE
        }else if(rating == 1.0f || rating > 1.0f) {
            binding.option3Star1Full.visibility = View.VISIBLE
        }
        // 누적
        if(rating > 1.0f && rating < 2.0f){
            binding.option3Star2Half.visibility = View.VISIBLE
        }else if(rating == 2.0f || rating > 2.0f){
            binding.option3Star2Full.visibility = View.VISIBLE
        }

        if(rating > 2.0f && rating < 3.0f){
            binding.option3Star3Half.visibility = View.VISIBLE
        }else if(rating == 3.0f || rating > 3.0f){
            binding.option3Star3Full.visibility = View.VISIBLE
        }

        if(rating > 3.0f && rating < 4.0f){
            binding.option3Star4Half.visibility = View.VISIBLE
        }else if(rating == 4.0f || rating > 4.0f){
            binding.option3Star4Full.visibility = View.VISIBLE
        }

        if(rating > 4.0f && rating < 5.0f){
            binding.option3Star5Half.visibility = View.VISIBLE
        }else if(rating == 5.0f || rating > 5.0f){
            binding.option3Star5Full.visibility = View.VISIBLE
        }
    }
    private fun updateRating_option4(rating : Float){
        if(rating == 0.0f){
            //
        } else if(rating > 0.0f && rating < 1.0f){
            binding.option4Star1Half.visibility = View.VISIBLE
        }else if(rating == 1.0f || rating > 1.0f) {
            binding.option4Star1Full.visibility = View.VISIBLE
        }
        // 누적
        if(rating > 1.0f && rating < 2.0f){
            binding.option4Star2Half.visibility = View.VISIBLE
        }else if(rating == 2.0f || rating > 2.0f){
            binding.option4Star2Full.visibility = View.VISIBLE
        }

        if(rating > 2.0f && rating < 3.0f){
            binding.option4Star3Half.visibility = View.VISIBLE
        }else if(rating == 3.0f || rating > 3.0f){
            binding.option4Star3Full.visibility = View.VISIBLE
        }

        if(rating > 3.0f && rating < 4.0f){
            binding.option4Star4Half.visibility = View.VISIBLE
        }else if(rating == 4.0f || rating > 4.0f){
            binding.option4Star4Full.visibility = View.VISIBLE
        }

        if(rating > 4.0f && rating < 5.0f){
            binding.option4Star5Half.visibility = View.VISIBLE
        }else if(rating == 5.0f || rating > 5.0f){
            binding.option4Star5Full.visibility = View.VISIBLE
        }
    }
}