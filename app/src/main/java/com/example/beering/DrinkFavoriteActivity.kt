package com.example.beering

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.beering.databinding.ActivityDrinkFavoriteBinding
import com.example.naverwebtoon.data.DrinkCover

class DrinkFavoriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityDrinkFavoriteBinding

    var drinkFavoriteAdapter: DrinkFavoriteAdapter? = null
    var drinkFavoriteList = ArrayList<DrinkCover>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrinkFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // DrinkFavoriteAdapter 초기화
        drinkFavoriteAdapter = DrinkFavoriteAdapter((drinkFavoriteList))
        binding.drinkFavoriteRv.adapter = drinkFavoriteAdapter
        binding.drinkFavoriteRv.layoutManager = GridLayoutManager(this,2)




        // api로 데이터 받아오는 부분 작성
        val data : ArrayList<DrinkCover> = ArrayList()

        data.add(0,
            DrinkCover("타이거",
                "Tiger",
                "Pilsner Urquell Brewery",
                1,
                R.drawable.img_temp_drink,
                true)
        )
        data.add(1,
            DrinkCover("타이거",
                "Tiger",
                "Pilsner Urquell Brewery",
                1,
                R.drawable.img_temp_drink,
            true)
        )
        data.add(2,
            DrinkCover("타이거",
                "Tiger",
                "Pilsner Urquell Brewery",
                1,
                R.drawable.img_temp_drink,
                true)
        )


        // 받아온 데이터 넣는 부분
        if (data != null) {
            initData(data)
        }

        drinkFavoriteAdapter!!.setOnItemClickListener(object : DrinkFavoriteAdapter.OnItemClickListener {
            override fun onItemClick(drinkInfo: DrinkCover) {

                val intent = Intent(this@DrinkFavoriteActivity, DrinkDetailActivity::class.java)
                intent.putExtra("drinkId", drinkInfo.id)
                startActivity(intent)
            }
        })


        drinkFavoriteAdapter!!.setOnHeartClickListener(object : DrinkFavoriteAdapter.OnHeartClickListener {
            override fun onButtonClick(position: Int) {
                drinkFavoriteAdapter!!.setBindHeart(position, true)
                drinkFavoriteAdapter!!.notifyItemChanged(position, "heartChange")
            }
        })

    }

    private fun initData(data: ArrayList<DrinkCover>) {
        drinkFavoriteList.addAll(data)
        drinkFavoriteAdapter?.notifyDataSetChanged()
    }



}