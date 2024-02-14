package com.example.beering.feature.my.drinkFavorite

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.beering.util.data.DrinkFavoriteResponse
import com.example.beering.util.token.token
import com.example.beering.util.getAccessToken
import com.example.beering.util.getMemberId
import com.example.beering.databinding.ActivityDrinkFavoriteBinding
import com.example.beering.feature.drink.DrinkDetailActivity
import com.example.beering.util.BaseActivity
import com.example.beering.util.data.DrinkCover
import com.example.beering.util.getRetrofit_header
import retrofit2.Call
import retrofit2.Response

class DrinkFavoriteActivity : BaseActivity<ActivityDrinkFavoriteBinding>(ActivityDrinkFavoriteBinding:: inflate) {
    var drinkFavoriteAdapter: DrinkFavoriteAdapter? = null
    var drinkFavoriteList = ArrayList<DrinkCover>()

    override fun initAfterBinding() {
        // api로 데이터 받아오는 부분 작성
        val data : ArrayList<DrinkCover> = ArrayList()

        val drinkFavoriteApiService =
            getRetrofit_header(getAccessToken(this@DrinkFavoriteActivity).toString()).create(
                DrinkFavoriteApiService::class.java
            )
        drinkFavoriteApiService.drinkFavorite(getMemberId(this@DrinkFavoriteActivity), null).enqueue(object : retrofit2.Callback<DrinkFavoriteResponse> {
            override fun onResponse(
                call: Call<DrinkFavoriteResponse>,
                response: Response<DrinkFavoriteResponse>
            ) {
                val resp = response.body()
                if(response.isSuccessful){
                    if (resp!!.isSuccess) {
                        for(drink in resp.result.content){
                            if(drink.primaryImageUrl.isEmpty()){
                                data.add(
                                    DrinkCover(
                                        drink.nameKr,
                                        drink.nameEn,
                                        drink.manufacturer,
                                        drink.drinkId,
                                        null,
                                        true
                                    )
                                )
                            } else {
                                data.add(
                                    DrinkCover(
                                        drink.nameKr,
                                        drink.nameEn,
                                        drink.manufacturer,
                                        drink.drinkId,
                                        drink.primaryImageUrl,
                                        true
                                    )
                                )
                            }

                        }


                        // 받아온 데이터 넣는 부분
                        if (data != null) {
                            initData(data)
                        }

                        // 상세 페이지 구현시, 구현
                        drinkFavoriteAdapter!!.setOnItemClickListener(object :
                            DrinkFavoriteAdapter.OnItemClickListener {
                            override fun onItemClick(drinkInfo: DrinkCover) {
                                val intent = Intent(this@DrinkFavoriteActivity, DrinkDetailActivity::class.java)
                                intent.putExtra("drinkId", drinkInfo.id)
                                startActivity(intent)

                            }


                        })


                        drinkFavoriteAdapter!!.setOnHeartClickListener(object :
                            DrinkFavoriteAdapter.OnHeartClickListener {
                            override fun onButtonClick(position: Int) {
                                drinkFavoriteAdapter!!.notifyItemChanged(position, "heartChange")
                            }
                        })

                    } else {
                        if(resp.responseCode == 2003) token(this@DrinkFavoriteActivity)
                    }
                }

            }

            override fun onFailure(call: Call<DrinkFavoriteResponse>, t: Throwable) {
                val builder = AlertDialog.Builder(this@DrinkFavoriteActivity)
                builder.setTitle("요청 오류")
                builder.setMessage("서버에 요청을 실패하였습니다.")
                builder.setPositiveButton("네") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }

        })



        // DrinkFavoriteAdapter 초기화
        drinkFavoriteAdapter = DrinkFavoriteAdapter((drinkFavoriteList))
        binding.drinkFavoriteRv.adapter = drinkFavoriteAdapter
        binding.drinkFavoriteRv.layoutManager = GridLayoutManager(this,2)
        binding.drinkFavoriteBackIv.setOnClickListener {
            finish()
        }

    }

    private fun initData(data: ArrayList<DrinkCover>) {
        drinkFavoriteList.addAll(data)
        drinkFavoriteAdapter?.notifyDataSetChanged()
    }



}