package com.example.beering

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.beering.databinding.ActivityReviewWritingBinding
import java.io.File
import android.Manifest
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naverwebtoon.data.DrinkCover

class ReviewWritingActivity: AppCompatActivity() {
    lateinit var binding: ActivityReviewWritingBinding
    var num_picture = 0
    val max_picture = 10

    var reviewPictureAdapter: ReviewPictureAdapter? = null
    var reviewPictureList = ArrayList<Uri>()

    //0~4번은 주류 개별 항목, 5번은 총평 항목
    var RbCheckedList : MutableList<Boolean> = mutableListOf(false, false, false, false, false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.reviewWritingCompleteButtonCl.isEnabled = false
        binding.reviewWritingCompleteButtonCl.setOnClickListener {
            // api 연결해서 내용들 서버에 보내기

            //닫기
            finish()
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

        // api로 갯수와 각각의 테스트 받아오기 (밑에꺼는 더미데이터)
        val apiList : List<String> = listOf("맛", "목넘김","향")




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
            binding.reviewWritingRating2Tv.text = ratingList.get(1)
            binding.reviewWritingRating3Cl.visibility = View.GONE
            binding.reviewWritingRating4Cl.visibility = View.GONE
            binding.reviewWritingRating5Cl.visibility = View.GONE

            RbCheckedList[2] = true
            RbCheckedList[3] = true
            RbCheckedList[4] = true
        } else if(ratingList.size < 4) {
            binding.reviewWritingRating3Tv.text = ratingList.get(2)
            binding.reviewWritingRating4Cl.visibility = View.GONE
            binding.reviewWritingRating5Cl.visibility = View.GONE
            RbCheckedList[3] = true
            RbCheckedList[4] = true
        } else if(ratingList.size < 5) {
            binding.reviewWritingRating4Tv.text = ratingList.get(3)
            binding.reviewWritingRating5Cl.visibility = View.GONE
            RbCheckedList[4] = true
        }





        // RatingBar 클릭했을 경우, 점수 표출
        binding.reviewWritingRating1Rb.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            binding.reviewWritingRating1ScoreTv.text = rating.toString()
            RbCheckedList[0] = true
            binding.reviewWritingRating1Mcv.setCardBackgroundColor(ContextCompat.getColor(this@ReviewWritingActivity, R.color.black))
            isButtonEanble()
        }
        binding.reviewWritingRating2Rb.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            binding.reviewWritingRating2ScoreTv.text = rating.toString()
            RbCheckedList[1] = true
            binding.reviewWritingRating2Mcv.setCardBackgroundColor(ContextCompat.getColor(this@ReviewWritingActivity, R.color.black))
            isButtonEanble()
        }
        binding.reviewWritingRating3Rb.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            binding.reviewWritingRating3ScoreTv.text = rating.toString()
            RbCheckedList[2] = true
            binding.reviewWritingRating3Mcv.setCardBackgroundColor(ContextCompat.getColor(this@ReviewWritingActivity, R.color.black))
            isButtonEanble()
        }
        binding.reviewWritingRating4Rb.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            binding.reviewWritingRating4ScoreTv.text = rating.toString()
            RbCheckedList[3] = true
            binding.reviewWritingRating4Mcv.setCardBackgroundColor(ContextCompat.getColor(this@ReviewWritingActivity, R.color.black))
            isButtonEanble()
        }
        binding.reviewWritingRating5Rb.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            binding.reviewWritingRating5ScoreTv.text = rating.toString()
            RbCheckedList[4] = true
            binding.reviewWritingRating5Mcv.setCardBackgroundColor(ContextCompat.getColor(this@ReviewWritingActivity, R.color.black))
            isButtonEanble()
        }

        binding.reviewWritingRatingTotalRb.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            binding.reviewWritingRatingTotalScoreTv.text = rating.toString()
            RbCheckedList[5] = true
            binding.reviewWritingRatingTotalMcv.setCardBackgroundColor(ContextCompat.getColor(this@ReviewWritingActivity, R.color.black))
            isButtonEanble()
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



