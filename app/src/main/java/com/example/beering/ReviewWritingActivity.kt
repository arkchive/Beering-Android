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
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naverwebtoon.data.DrinkCover

class ReviewWritingActivity: AppCompatActivity() {
    lateinit var binding: ActivityReviewWritingBinding
    var num_picture = 0
    val max_picture = 10

    var reviewPictureAdapter: ReviewPictureAdapter? = null
    var reviewPictureList = ArrayList<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.reviewWritingPictureOffIv.setOnClickListener {
            selectGallery()

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

        binding.reviewWritingPictureOnIv.setOnClickListener {
            selectGallery()
        }

        binding.reviewWritingBackIv.setOnClickListener {
            finish()
        }


        reviewPictureAdapter = ReviewPictureAdapter((reviewPictureList))
        binding.reviewWritingPictureRv.adapter = reviewPictureAdapter
        binding.reviewWritingPictureRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }






    companion object{
        const val REVIEW_MIN_LENGTH = 10
        // 갤러리 권한 요청
        const val REQ_GALLERY = 1

        // API 호출시 Parameter key값
        const val PARAM_KEY_IMAGE = "image"
        const val PARAM_KEY_PRODUCT_ID = "product_id"
        const val PARAM_KEY_REVIEW = "review_content"
        const val PARAM_KEY_RATING = "rating"
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



