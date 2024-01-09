package com.example.beering.util.like

import android.app.AlertDialog
import android.content.Context
import com.example.beering.util.token.token
import com.example.beering.util.getAccessToken
import com.example.beering.util.getRetrofit_header
import retrofit2.Call
import retrofit2.Response

fun DrinkLike (context: Context, memberId: Int, drinkId:Int){
    val drinkLikeService =
        getRetrofit_header(getAccessToken(context).toString()).create(LikeApiService::class.java)
    drinkLikeService.like(memberId,drinkId).enqueue(object : retrofit2.Callback<DrinkLikeResponse> {
        override fun onResponse(
            call: Call<DrinkLikeResponse>,
            response: Response<DrinkLikeResponse>
        ) {
            if(response.isSuccessful) {
                val resp = response.body()
                if (resp!!.isSuccess) {

                } else {
                    if (resp.responseCode == 2003) token(context)
                    if(resp.responseCode == 2200){
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("접근 오류")
                        builder.setMessage("잘못된 접근입니다.")
                        builder.setPositiveButton("네") { dialog, which ->
                            dialog.dismiss()
                        }
                        val dialog = builder.create()
                        dialog.show()
                    }

                }
            }
        }

        override fun onFailure(call: Call<DrinkLikeResponse>, t: Throwable) {
            val builder = AlertDialog.Builder(context)
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

fun ReviewLike (context: Context, memberId: Int, reviewId:Int){
    val ReviewLikeService =
        getRetrofit_header(getAccessToken(context).toString()).create(LikeApiService::class.java)
    ReviewLikeService.like(memberId,reviewId).enqueue(object : retrofit2.Callback<DrinkLikeResponse> {
        override fun onResponse(
            call: Call<DrinkLikeResponse>,
            response: Response<DrinkLikeResponse>
        ) {
            if(response.isSuccessful) {
                val resp = response.body()
                if (resp!!.isSuccess) {

                } else {
                    if (resp.responseCode == 2003) token(context)
                    if(resp.responseCode == 2200){
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("접근 오류")
                        builder.setMessage("잘못된 접근입니다.")
                        builder.setPositiveButton("네") { dialog, which ->
                            dialog.dismiss()
                        }
                        val dialog = builder.create()
                        dialog.show()
                    }

                }
            }
        }

        override fun onFailure(call: Call<DrinkLikeResponse>, t: Throwable) {
            val builder = AlertDialog.Builder(context)
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