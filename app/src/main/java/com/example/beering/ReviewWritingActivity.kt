package com.example.beering

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.beering.databinding.ActivityReviewWritingBinding
import java.io.File
import android.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beering.api.ReviewSelectedOptions
import com.example.beering.api.ReviewWritingApiService
import com.example.beering.api.ReviewWritingFormApiService
import com.example.beering.api.ReviewWritingFormResponse
import com.example.beering.api.ReviewWritingRequest
import com.example.beering.api.ReviewWritingResponse
import com.example.beering.api.getRetrofit_header
import com.example.beering.api.getRetrofit_sync
import com.example.beering.api.token
import com.example.beering.data.getAccessToken
import com.example.beering.data.getMemberId
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response

class ReviewWritingActivity: AppCompatActivity() {
    lateinit var binding: ActivityReviewWritingBinding
    var num_picture = 0
    val max_picture = 10
    var imageFiles: MutableList<File> = ArrayList()
    var reviewOptionIdList: MutableList<Int> = ArrayList()
    var reviewratingList: MutableList<Float> = ArrayList()
    var reviewRatingTotal: Float = 0F

    val apiList : MutableList<String> = ArrayList()
    val drinkId: Int = 2  // 더미 데이터


    var reviewPictureAdapter: ReviewPictureAdapter? = null
    var reviewPictureList = ArrayList<Uri>()

    //0~4번은 주류 개별 항목, 5번은 총평 항목
    var RbCheckedList : MutableList<Boolean> = mutableListOf(false, false, false, false, false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // api연결하여 작성 폼 설정
        val reviewWritingFromApi = getRetrofit_sync().create(ReviewWritingFormApiService::class.java)
        reviewWritingFromApi.getform(drinkId).enqueue(object : retrofit2.Callback<ReviewWritingFormResponse>{
            override fun onResponse(
                call: Call<ReviewWritingFormResponse>,
                response: Response<ReviewWritingFormResponse>
            ) {
                if(response.isSuccessful) {
                    val resp = response.body()
                    if (resp!!.isSuccess) {
                        for(result in resp.result){
                            reviewOptionIdList.add(result.reviewOptionId)
                            apiList.add(result.name)
                        }

                        val ratingList : List<String> = apiList
                        if(ratingList.size < 2) {
                            binding.reviewWritingRating1Tv.text = ratingList.get(0)
                            binding.reviewWritingRating2Cl.visibility = View.GONE
                            binding.reviewWritingRating3Cl.visibility = View.GONE
                            binding.reviewWritingRating4Cl.visibility = View.GONE
                            binding.reviewWritingRating5Cl.visibility = View.GONE

                            RbCheckedList[1] = true
                            RbCheckedList[2] = true
                            RbCheckedList[3] = true
                            RbCheckedList[4] = true
                        }else if(ratingList.size < 3) {
                            binding.reviewWritingRating1Tv.text = ratingList.get(0)
                            binding.reviewWritingRating2Tv.text = ratingList.get(1)
                            binding.reviewWritingRating3Cl.visibility = View.GONE
                            binding.reviewWritingRating4Cl.visibility = View.GONE
                            binding.reviewWritingRating5Cl.visibility = View.GONE

                            RbCheckedList[2] = true
                            RbCheckedList[3] = true
                            RbCheckedList[4] = true
                        } else if(ratingList.size < 4) {
                            binding.reviewWritingRating1Tv.text = ratingList.get(0)
                            binding.reviewWritingRating2Tv.text = ratingList.get(1)
                            binding.reviewWritingRating3Tv.text = ratingList.get(2)
                            binding.reviewWritingRating4Cl.visibility = View.GONE
                            binding.reviewWritingRating5Cl.visibility = View.GONE
                            RbCheckedList[3] = true
                            RbCheckedList[4] = true
                        } else if(ratingList.size < 5) {
                            binding.reviewWritingRating1Tv.text = ratingList.get(0)
                            binding.reviewWritingRating2Tv.text = ratingList.get(1)
                            binding.reviewWritingRating3Tv.text = ratingList.get(2)
                            binding.reviewWritingRating4Tv.text = ratingList.get(3)
                            binding.reviewWritingRating5Cl.visibility = View.GONE
                            RbCheckedList[4] = true
                        } else if(ratingList.size < 6) {
                            binding.reviewWritingRating1Tv.text = ratingList.get(0)
                            binding.reviewWritingRating2Tv.text = ratingList.get(1)
                            binding.reviewWritingRating3Tv.text = ratingList.get(2)
                            binding.reviewWritingRating4Tv.text = ratingList.get(3)
                            binding.reviewWritingRating5Tv.text = ratingList.get(4)
                        }





                        // RatingBar 클릭했을 경우, 점수 표출
                        binding.reviewWritingRating1Rb.setOnRatingChangeListener { ratingBar, rating, fromUser ->
                            binding.reviewWritingRating1ScoreTv.text = rating.toString()
                            reviewratingList.add(rating)
                            RbCheckedList[0] = true
                            binding.reviewWritingRating1Mcv.setCardBackgroundColor(ContextCompat.getColor(this@ReviewWritingActivity, R.color.black))
                            isButtonEanble()
                        }
                        binding.reviewWritingRating2Rb.setOnRatingChangeListener { ratingBar, rating, fromUser ->
                            binding.reviewWritingRating2ScoreTv.text = rating.toString()
                            reviewratingList.add(rating)
                            RbCheckedList[1] = true
                            binding.reviewWritingRating2Mcv.setCardBackgroundColor(ContextCompat.getColor(this@ReviewWritingActivity, R.color.black))
                            isButtonEanble()
                        }
                        binding.reviewWritingRating3Rb.setOnRatingChangeListener { ratingBar, rating, fromUser ->
                            binding.reviewWritingRating3ScoreTv.text = rating.toString()
                            reviewratingList.add(rating)
                            RbCheckedList[2] = true
                            binding.reviewWritingRating3Mcv.setCardBackgroundColor(ContextCompat.getColor(this@ReviewWritingActivity, R.color.black))
                            isButtonEanble()
                        }
                        binding.reviewWritingRating4Rb.setOnRatingChangeListener { ratingBar, rating, fromUser ->
                            binding.reviewWritingRating4ScoreTv.text = rating.toString()
                            reviewratingList.add(rating)
                            RbCheckedList[3] = true
                            binding.reviewWritingRating4Mcv.setCardBackgroundColor(ContextCompat.getColor(this@ReviewWritingActivity, R.color.black))
                            isButtonEanble()
                        }
                        binding.reviewWritingRating5Rb.setOnRatingChangeListener { ratingBar, rating, fromUser ->
                            binding.reviewWritingRating5ScoreTv.text = rating.toString()
                            reviewratingList.add(rating)
                            RbCheckedList[4] = true
                            binding.reviewWritingRating5Mcv.setCardBackgroundColor(ContextCompat.getColor(this@ReviewWritingActivity, R.color.black))
                            isButtonEanble()
                        }

                        binding.reviewWritingRatingTotalRb.setOnRatingChangeListener { ratingBar, rating, fromUser ->
                            binding.reviewWritingRatingTotalScoreTv.text = rating.toString()
                            reviewRatingTotal = rating
                            RbCheckedList[5] = true
                            binding.reviewWritingRatingTotalMcv.setCardBackgroundColor(ContextCompat.getColor(this@ReviewWritingActivity, R.color.black))
                            isButtonEanble()
                        }

                    } else {
                        if (resp.responseCode == 2003) {
                            token(this@ReviewWritingActivity)
                            finish()
                        }

                    }
                }
            }

            override fun onFailure(call: Call<ReviewWritingFormResponse>, t: Throwable) {
                val builder = AlertDialog.Builder(this@ReviewWritingActivity)
                builder.setTitle("요청 오류")
                builder.setMessage("서버에 요청을 실패하였습니다.")
                builder.setPositiveButton("네") { dialog, which ->
                    dialog.dismiss()
                    finish()
                }
                val dialog = builder.create()
                dialog.show()
            }

        })



        binding.reviewWritingCompleteButtonCl.isEnabled = false
        binding.reviewWritingCompleteButtonCl.setOnClickListener {
            // api 연결해서 내용들 서버에 보내기




            val reviewWritingService =
                getRetrofit_header(getAccessToken(this@ReviewWritingActivity).toString()).create(ReviewWritingApiService::class.java)

            val reviewSelectedOptions : MutableList<ReviewSelectedOptions> = ArrayList()

            for(i in reviewOptionIdList){
                reviewSelectedOptions.add(ReviewSelectedOptions(i, reviewratingList[i-1]))
            }


            val jsonData = ReviewWritingRequest(binding.reviewWritingImpressionEd.text.toString(), reviewRatingTotal, reviewSelectedOptions)
            val requestDtoJson = Gson().toJson(jsonData)
            val requestDtoPart = MultipartBody.Part.createFormData("requestDto", null, requestDtoJson.toRequestBody("application/json".toMediaType()))

            if(imageFiles.isEmpty()){
                imageFiles.add(File(""))
            }
            Log.d("test", imageFiles.toString())

            val imageParts = imageFiles.map { file ->
                val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("reviewImages", file.name, requestBody)
            }

            Log.d("test2", imageParts.toString())




            reviewWritingService.post(requestDtoPart, imageParts, getMemberId(this@ReviewWritingActivity), drinkId).enqueue(object : retrofit2.Callback<ReviewWritingResponse> {
                override fun onResponse(
                    call: Call<ReviewWritingResponse>,
                    response: Response<ReviewWritingResponse>
                ) {
                    if(response.isSuccessful) {
                        val resp = response.body()
                        if (resp!!.isSuccess) {
                            val builder = AlertDialog.Builder(this@ReviewWritingActivity)
                            builder.setTitle("등록 완료")
                            builder.setMessage("리뷰가 등록되었습니다.")
                            builder.setPositiveButton("네") { dialog, which ->
                                dialog.dismiss()
                                //닫기
                                finish()
                            }
                            val dialog = builder.create()
                            dialog.show()

                        } else {
                            if (resp.responseCode == 2003) token(this@ReviewWritingActivity)

                        }
                    }
                }

                override fun onFailure(call: Call<ReviewWritingResponse>, t: Throwable) {
                    Log.e("ReviewWritingActivity", "API 요청 실패", t)
                    val builder = AlertDialog.Builder(this@ReviewWritingActivity)
                    builder.setTitle("요청 오류")
                    builder.setMessage("서버에 요청을 실패하였습니다.")
                    builder.setPositiveButton("네") { dialog, which ->
                        dialog.dismiss()
                    }
                    val dialog = builder.create()
                    dialog.show()
                }

            })


        }

        binding.reviewWritingImpressionEd.addTextChangedListener(object: TextWatcher {
            // 입력 하기 전에 작동
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            //값 변경 시 실행되는 함수
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isButtonEanble()
            }

            // 입력이 끝날 때 작동
            override fun afterTextChanged(p0: Editable?) {}
        })



        // 사진 불러오기
        binding.reviewWritingPictureOffIv.setOnClickListener {
            selectGallery()

            // 사진마다(item마다) 삭제버튼 적용
            reviewPictureAdapter!!.setOnCancelClickListener(object : ReviewPictureAdapter.OnCancelClickListener {
                override fun onCancelClick(imageUri: Uri) {
                    num_picture -= 1
                    reviewPictureList.remove(imageUri)
                    imageUri?.let {

                        // 서버 업로드를 위해 파일 형태로 변환한다
                        var imageFile = File(getRealPathFromURI(it))
                        imageFiles.remove(imageFile)
                    }
                    reviewPictureAdapter!!.notifyDataSetChanged()
                    binding.reviewWritingPictureNumTv.text = num_picture.toString()

                    if(num_picture == 0) {
                        binding.reviewWritingPictureOffIv.visibility = View.VISIBLE
                        binding.reviewWritingPictureOnIv.visibility = View.INVISIBLE
                    } else if (num_picture < max_picture) {
                        binding.reviewWritingPictureOnIv.isEnabled = true
                    }
                }
            })
        }


        // 사진 불러오기
        binding.reviewWritingPictureOnIv.setOnClickListener {
            selectGallery()
        }
        // 뒤로 나가기
        binding.reviewWritingBackIv.setOnClickListener {
            finish()
        }













        reviewPictureAdapter = ReviewPictureAdapter((reviewPictureList))
        binding.reviewWritingPictureRv.adapter = reviewPictureAdapter
        binding.reviewWritingPictureRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)




    }

    fun isButtonEanble(){
        if(binding.reviewWritingImpressionEd.text.isNotEmpty() && RbCheckedList.all { it })
        {
            binding.reviewWritingCompleteButtonCl.isEnabled = true
            binding.reviewWritingCompleteButtonCl.setBackgroundColor(ContextCompat.getColor(this@ReviewWritingActivity, R.color.black))

        }
    }





    // 이미지를 결과값으로 받는 변수
    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
            result ->
        if(result.resultCode == RESULT_OK){
            // 이미지를 받으면 ImageView에 적용한다
            val imageUri = result.data?.data

            imageUri?.let{

                // 서버 업로드를 위해 파일 형태로 변환한다
                var imageFile = File(getRealPathFromURI(it))
                imageFiles.add(imageFile)

                // rv의 list에 imageUri 추가
                reviewPictureList.add(imageUri)
                reviewPictureAdapter?.notifyDataSetChanged()
                if(num_picture == 0)
                {
                    binding.reviewWritingPictureOffIv.visibility = View.INVISIBLE
                    binding.reviewWritingPictureOnIv.visibility = View.VISIBLE
                }
                num_picture += 1
                binding.reviewWritingPictureNumTv.text = num_picture.toString()

                if(num_picture == max_picture){
                    binding.reviewWritingPictureOnIv.isEnabled = false
                }

            }


        }
    }




    fun getRealPathFromURI(uri: Uri): String {

        val buildName = Build.MANUFACTURER
        if (buildName.equals("Xiaomi")) {
            return uri.path!!
        }
        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }

    private fun selectGallery(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        imageResult.launch(intent)

    }



}



