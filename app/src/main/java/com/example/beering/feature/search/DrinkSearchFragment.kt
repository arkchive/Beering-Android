package com.example.beering.feature.search

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.beering.util.data.DrinkCoverResponse
import com.example.beering.util.token.token
import com.example.beering.databinding.FragmentDrinkSearchBinding
import com.example.beering.feature.drink.DrinkDetailActivity
import com.example.beering.util.data.DrinkCover
import com.example.beering.util.getAccessToken
import com.example.beering.util.getRetrofit_header
import retrofit2.Call
import retrofit2.Response

class DrinkSearchFragment : Fragment() {
    lateinit var binding: FragmentDrinkSearchBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    // 선택상태 저장 변수
    var searchType: String? = null
    var searchSort: MutableList<String> = ArrayList()
    var searchPrice_min: Int? = null
    var searchPrice_max: Int? = null



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

        binding.drinkSearchTopSearchEd.setOnKeyListener { view, i, keyEvent ->
            if ((keyEvent.action == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                val data: ArrayList<DrinkCover> = ArrayList()
                // 엔터가 눌릴 때 하고 싶은 일
                // api로 데이터 받아오는 부분 작성 -> 이거를 버튼 눌렀을때로 변경해야할듯

                val drinkSearchService =
                    getRetrofit_header(getAccessToken(requireContext()).toString()).create(
                        DrinkSearchApiService::class.java
                    )
                val category = searchSort.joinToString(",")
                drinkSearchService.drinkSearch(null,binding.drinkSearchTopSearchEd.text.toString(), searchType, category, searchPrice_min, searchPrice_max).enqueue(object : retrofit2.Callback<DrinkCoverResponse> {
                    override fun onResponse(
                        call: Call<DrinkCoverResponse>,
                        response: Response<DrinkCoverResponse>
                    ) {
                        val resp = response.body()
                        drinkSearchAdapter?.clearItems()
                        searchSort.clear()
                        if(response.isSuccessful){
                            if (resp!!.isSuccess) {
                                for(drink in resp.result.content){
                                    if(drink.imageUrlList.isEmpty()){
                                        data.add(
                                            DrinkCover(
                                                drink.nameKr,
                                                drink.nameEn,
                                                drink.manufacturer,
                                                drink.drinkId,
                                                null,
                                                drink.isLiked
                                            )
                                        )
                                    } else {
                                        data.add(
                                            DrinkCover(
                                                drink.nameKr,
                                                drink.nameEn,
                                                drink.manufacturer,
                                                drink.drinkId,
                                                drink.imageUrlList[0],
                                                drink.isLiked
                                            )

                                        )
                                    }

                                }


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
                                        drinkSearchAdapter!!.notifyItemChanged(position, "heartChange")
                                    }
                                })

                            } else {
                                if(resp.responseCode == 2003) token(requireContext())
                            }
                        }

                    }

                    override fun onFailure(call: Call<DrinkCoverResponse>, t: Throwable) {
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

                true
            } else false


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

        binding.drinkSearchTopSearchCancelIv.setOnClickListener {
            binding.drinkSearchTopSearchEd.text.clear()
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
            searchSort.add("beer")
        }
        if (bundle.getBoolean("isTypeWine")) {
            binding.drinkSearchTypeMcv.visibility = View.VISIBLE

            typeList.add("와인")
            searchSort.add("wine")

        }
        if (bundle.getBoolean("isTypeTraditional")) {
            binding.drinkSearchTypeMcv.visibility = View.VISIBLE

            typeList.add("전통주")
            searchSort.add("traditional_liquor")
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
            searchType = "name"
        } else if (bundle.getBoolean("isReview")) {
            binding.drinkSearchSortMcv.visibility = View.VISIBLE
            binding.drinkSearchSortTv.text = "리뷰많은순"
            searchType = "review"
        } else if (bundle.getBoolean("isLowPrice")) {
            binding.drinkSearchSortMcv.visibility = View.VISIBLE
            binding.drinkSearchSortTv.text = "최저가순"
            searchType = "price"
        } else if (bundle.getBoolean("isScore")) {
            binding.drinkSearchSortMcv.visibility = View.VISIBLE
            binding.drinkSearchSortTv.text = "평점순"
            searchType = "rating"
        } else {
            binding.drinkSearchSortMcv.visibility = View.GONE
        }


        if (bundle.getInt("minPrice") != -1 && bundle.getInt("maxPrice") != -1) {
            binding.drinkSearchPriceMcv.visibility = View.VISIBLE
            binding.drinkSearchPriceTv.text = bundle.getInt("minPrice")
                .toString() + "원 ~ " + bundle.getInt("maxPrice").toString() + "원"
            searchPrice_min = bundle.getInt("minPrice")
            searchPrice_max = bundle.getInt("maxPrice")
        } else if (bundle.getInt("minPrice") != -1 && bundle.getInt("maxPrice") == -1) {
            binding.drinkSearchPriceMcv.visibility = View.VISIBLE
            binding.drinkSearchPriceTv.text =
                bundle.getInt("minPrice").toString() + "원 이상"
            searchPrice_min = bundle.getInt("minPrice")
        } else if (bundle.getInt("minPrice") == -1 && bundle.getInt("maxPrice") != -1) {
            binding.drinkSearchPriceMcv.visibility = View.VISIBLE
            binding.drinkSearchPriceTv.text =
                bundle.getInt("maxPrice").toString() + "원 이하"
            searchPrice_max = bundle.getInt("maxPrice")
        } else {
            binding.drinkSearchPriceMcv.visibility = View.GONE
        }
    }


    private fun initData(data: ArrayList<DrinkCover>) {
        drinkSearchList.addAll(data)
        drinkSearchAdapter?.notifyDataSetChanged()
    }


}