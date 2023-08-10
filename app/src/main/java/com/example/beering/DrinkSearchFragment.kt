package com.example.beering

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.beering.api.MyApiService
import com.example.beering.api.MyResponse
import com.example.beering.api.getRetrofit_header
import com.example.beering.api.token
import com.example.beering.data.getAccessToken
import com.example.beering.databinding.FragmentDrinkSearchBinding
import com.example.naverwebtoon.data.DrinkCover
import retrofit2.Call
import retrofit2.Response

class DrinkSearchFragment : Fragment() {
    lateinit var binding: FragmentDrinkSearchBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    companion object { // static 프로퍼티
        const val GET_DATA = 1
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrinkSearchBinding.inflate(inflater, container, false)



        return binding.root

    }

    var drinkSearchAdapter: DrinkSearchAdapter? = null
    var drinkSearchList = ArrayList<DrinkCover>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.drinkSearchTopSearchCancelIv.setOnClickListener {
            // api로 데이터 받아오는 부분 작성 -> 이거를 버튼 눌렀을때로 변경해야할듯


            val data: ArrayList<DrinkCover> = ArrayList()

            data.add(
                0,
                DrinkCover(
                    "타이거",
                    "Tiger",
                    "Pilsner Urquell Brewery",
                    1,
                    R.drawable.img_temp_drink
                )
            )
            data.add(
                1,
                DrinkCover(
                    "타이거",
                    "Tiger",
                    "Pilsner Urquell Brewery",
                    2,
                    R.drawable.img_temp_drink
                )
            )
            data.add(
                2,
                DrinkCover(
                    "타이거",
                    "Tiger",
                    "Pilsner Urquell Brewery",
                    3,
                    R.drawable.img_temp_drink
                )
            )


            // 받아온 데이터 넣는 부분
            if (data != null) {
                initData(data)
            }

            // 상세 페이지 구현시, 구현
            drinkSearchAdapter!!.setOnItemClickListener(object :
                DrinkSearchAdapter.OnItemClickListener {
                override fun onItemClick(drinkInfo: DrinkCover) {
                    val intent = Intent(requireContext(), DrinkDetailActivity::class.java)
                    intent.putExtra("drinkId", drinkInfo.id)
                    startActivity(intent)

                }


            })


            drinkSearchAdapter!!.setOnHeartClickListener(object :
                DrinkSearchAdapter.OnHeartClickListener {
                override fun onButtonClick(position: Int) {
                    drinkSearchAdapter!!.setBindHeart(position, true)
                    drinkSearchAdapter!!.notifyItemChanged(position, "heartChange")
                }
            })

        }



        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val bundle = data?.extras
                    if (bundle != null) {
                        updateWithFilter(bundle)

                    }
                }
            }

        binding.drinkSearchButtonFilterMcv.setOnClickListener {
            // FilterActivity 시작
            val intent = Intent(requireContext(), SearchFilterActivity::class.java)
            resultLauncher.launch(intent)
        }







        drinkSearchAdapter = DrinkSearchAdapter((drinkSearchList))
        binding.drinkSearchResultRv.adapter = drinkSearchAdapter
        binding.drinkSearchResultRv.layoutManager = GridLayoutManager(context, 2)


    }

    private fun updateWithFilter(bundle: Bundle) {
        // 여기서 filterData를 사용하여 프래그먼트 UI 업데이트를 수행합니다.
        // 필요한 경우 어댑터에서 notifyDataSetChanged()를 호출하는 것을 잊지 마세요.
        val typeList = mutableListOf<String>()

        // 주종
        if (bundle.getBoolean("isTypeBeer")) {
            binding.drinkSearchTypeMcv.visibility = View.VISIBLE
            typeList.add("맥주")
        }
        if (bundle.getBoolean("isTypeWine")) {
            binding.drinkSearchTypeMcv.visibility = View.VISIBLE

            typeList.add("와인")
        }
        if (bundle.getBoolean("isTypeTraditional")) {
            binding.drinkSearchTypeMcv.visibility = View.VISIBLE

            typeList.add("전통주")
        }

        if (bundle.getBoolean("isTypeBeer") || bundle.getBoolean("isTypeWine") || bundle.getBoolean(
                "isTypeTraditional"
            )
        ) {
            binding.drinkSearchTypeTv.text = typeList.joinToString(", ")
        } else {
            binding.drinkSearchTypeMcv.visibility = View.GONE

            binding.drinkSearchTypeTv.text = "주종"
        }


        // 정렬기준
        if (bundle.getBoolean("isName")) {
            binding.drinkSearchSortMcv.visibility = View.VISIBLE
            binding.drinkSearchSortTv.text = "이름순"
        } else if (bundle.getBoolean("isReview")) {
            binding.drinkSearchSortMcv.visibility = View.VISIBLE
            binding.drinkSearchSortTv.text = "리뷰많은순"
        } else if (bundle.getBoolean("isLowPrice")) {
            binding.drinkSearchSortMcv.visibility = View.VISIBLE
            binding.drinkSearchSortTv.text = "최저가순"
        } else if (bundle.getBoolean("isScore")) {
            binding.drinkSearchSortMcv.visibility = View.VISIBLE
            binding.drinkSearchSortTv.text = "평점순"
        } else {
            binding.drinkSearchSortMcv.visibility = View.GONE
        }


        if (bundle.getInt("minPrice") != -1 && bundle.getInt("maxPrice") != -1) {
            binding.drinkSearchPriceMcv.visibility = View.VISIBLE
            binding.drinkSearchPriceTv.text = bundle.getInt("minPrice")
                .toString() + "원 ~ " + bundle.getInt("maxPrice").toString() + "원"
        } else if (bundle.getInt("minPrice") != -1 && bundle.getInt("maxPrice") == -1) {
            binding.drinkSearchPriceMcv.visibility = View.VISIBLE
            binding.drinkSearchPriceTv.text =
                bundle.getInt("minPrice").toString() + "원 이상"
        } else if (bundle.getInt("minPrice") == -1 && bundle.getInt("maxPrice") != -1) {
            binding.drinkSearchPriceMcv.visibility = View.VISIBLE
            binding.drinkSearchPriceTv.text =
                bundle.getInt("maxPrice").toString() + "원 이하"
        } else {
            binding.drinkSearchPriceMcv.visibility = View.GONE
        }
    }


    private fun initData(data: ArrayList<DrinkCover>) {
        drinkSearchList.addAll(data)
        drinkSearchAdapter?.notifyDataSetChanged()
    }


}