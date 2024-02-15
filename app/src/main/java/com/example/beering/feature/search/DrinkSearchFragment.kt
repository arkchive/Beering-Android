package com.example.beering.feature.search

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.beering.R
import com.example.beering.util.data.DrinkCoverResponse
import com.example.beering.util.token.token
import com.example.beering.databinding.FragmentDrinkSearchBinding
import com.example.beering.feature.drink.DrinkDetailActivity
import com.example.beering.feature.home.ReviewsApiService
import com.example.beering.util.data.DrinkCover
import com.example.beering.util.getAccessToken
import com.example.beering.util.getRetrofit_header
import com.example.beering.util.getRetrofit_no_header
import com.example.beering.util.stateLogin
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import retrofit2.Call
import retrofit2.Response

class DrinkSearchFragment : Fragment() {
    lateinit var binding: FragmentDrinkSearchBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    // 선택상태 저장 변수
    var searchPrice_min: Int? = null
    var searchPrice_max: Int? = null


    // 필터 선택 상태 저장 변수
    var searchType: String? = null // 정렬
    var searchSort: String? = null // 주종

    // 필터 선택 버튼
    var searchSort_filter = Array<Boolean>(6){false}
    // 0 : 맥주,  1: 와인, 2: 전통주, 3: 위스키, 4: 리큐르, 5: 보드카
    var searchType_filter = Array<Boolean>(4){false}
    // 0: 이름순, 1: 리뷰많은순, 2: 최저가순, 3: 평점순

    var searchCountry : String? = null
    var searchTagList = mutableListOf<String>()    // 필터 태그

    // 필터 하위 옵션
    var searchSweetness: Int? = null // 와인 하위옵션


    var saerchButton_sort: Boolean =false
    var saerchButton_type: Boolean =false




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

        // Slding Up Panel Layout

        val slidePanel = binding.drinkSearchSupl

        slidePanel.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                // 패널이 움직일 때의 동작
            }

            override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
                // 패널 상태 변경 시의 동작
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    // 패널이 열렸을 때
                    binding.drinkSearchTopSearchEd.isFocusable = false
                } else if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    // 패널이 닫혔을 때
                    binding.drinkSearchTopSearchEd.isFocusable = true
                    binding.drinkSearchTopSearchEd.isFocusableInTouchMode = true
                }
            }
        })



        binding.drinkSearchButtonFilterMcv.setOnClickListener {
            val state = slidePanel.panelState
            // 닫힌 상태일 경우 열기
            if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            }
            // 열린 상태일 경우 닫기
            else if (state == SlidingUpPanelLayout.PanelState.EXPANDED) {
                slidePanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }
        }


        // filter의 버튼들에 따른 동적 ui
        binding.drinkSearchButtonFilterBeerTv.setOnClickListener(searchFilterButton_sort(0))
        binding.drinkSearchButtonFilterWineTv.setOnClickListener(searchFilterButton_sort(1))
        binding.drinkSearchButtonFilterTraditionalLiquorTv.setOnClickListener(searchFilterButton_sort(2))
        binding.drinkSearchButtonFilterWhiskeyTv.setOnClickListener(searchFilterButton_sort(3))
        binding.drinkSearchButtonFilterLiqueurTv.setOnClickListener(searchFilterButton_sort(4))
        binding.drinkSearchButtonFilterVodkaTv.setOnClickListener(searchFilterButton_sort(5))

        binding.drinkSearchButtonFilterNameTv.setOnClickListener(searchFilterButton_type(0))
        binding.drinkSearchButtonFilterReviewTv.setOnClickListener(searchFilterButton_type(1))
        binding.drinkSearchButtonFilterLowPriceTv.setOnClickListener(searchFilterButton_type(2))
        binding.drinkSearchButtonFilterHighRatingTv.setOnClickListener(searchFilterButton_type(3))

        binding.drinkSearchButtonFilterApplyMcv.setOnClickListener {
            val state = slidePanel.panelState
            // 닫힌 상태일 경우 열기
            if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            }
            // 열린 상태일 경우 닫기
            else if (state == SlidingUpPanelLayout.PanelState.EXPANDED) {
                slidePanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }


            // 주종 적용
            val sortButton_check = searchSort_filter.indexOf(true)
            if(sortButton_check >= 0) {
                binding.drinkSearchSortMcv.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.beering_black
                    )
                )
                binding.drinkSearchSortTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.beering_white
                    )
                )

                when (sortButton_check) {
                    0 -> binding.drinkSearchTypeTv.text = "맥주"
                    1 -> binding.drinkSearchTypeTv.text = "와인"
                    2 -> binding.drinkSearchTypeTv.text = "전통준"
                    3 -> binding.drinkSearchTypeTv.text = "위스키"
                    4 -> binding.drinkSearchTypeTv.text = "리큐르"
                    5 -> binding.drinkSearchTypeTv.text = "보드카"
                }
            }

            // 정렬 기준 적용
            val typeButton_check = searchType_filter.indexOf(true)
            if(typeButton_check >= 0) {
                binding.drinkSearchTypeMcv.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.beering_black
                    )
                )
                binding.drinkSearchTypeTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.beering_white
                    )
                )

                when (typeButton_check) {
                    0 -> binding.drinkSearchSortTv.text = "이름순"
                    1 -> binding.drinkSearchSortTv.text = "리뷰많은순"
                    2 -> binding.drinkSearchSortTv.text = "최저가순"
                    3 -> binding.drinkSearchSortTv.text = "평점순"
                }
            }
        }



        val data: ArrayList<DrinkCover> = ArrayList()
        // 엔터가 눌릴 때 하고 싶은 일
        // api로 데이터 받아오는 부분 작성 -> 이거를 버튼 눌렀을때로 변경해야할듯


        var drinkSearchService: DrinkSearchApiService

        // api 연결
        if(stateLogin(requireContext())){
            drinkSearchService =
                getRetrofit_header(getAccessToken(requireContext()).toString()).create(
                    DrinkSearchApiService::class.java)
        } else {
            drinkSearchService =
                getRetrofit_no_header().create(DrinkSearchApiService::class.java)
        }


        drinkSearchService.drinkSearch(null,binding.drinkSearchTopSearchEd.text.toString(), searchType, searchSort, searchPrice_min, searchPrice_max, searchCountry, searchTagList.joinToString(","), searchSweetness).enqueue(object : retrofit2.Callback<DrinkCoverResponse> {
            override fun onResponse(
                call: Call<DrinkCoverResponse>,
                response: Response<DrinkCoverResponse>
            ) {
                val resp = response.body()
                drinkSearchAdapter?.clearItems()
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













        binding.drinkSearchTopSearchEd.setOnEditorActionListener { view, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH ||  (keyEvent != null && keyEvent.action == KeyEvent.ACTION_DOWN && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) {
                val data: ArrayList<DrinkCover> = ArrayList()
                // 엔터가 눌릴 때 하고 싶은 일
                // api로 데이터 받아오는 부분 작성 -> 이거를 버튼 눌렀을때로 변경해야할듯


                var drinkSearchService: DrinkSearchApiService

                // api 연결
                if(stateLogin(requireContext())){
                    drinkSearchService =
                        getRetrofit_header(getAccessToken(requireContext()).toString()).create(
                            DrinkSearchApiService::class.java)
                } else {
                    drinkSearchService =
                        getRetrofit_no_header().create(DrinkSearchApiService::class.java)
                }


                drinkSearchService.drinkSearch(null,binding.drinkSearchTopSearchEd.text.toString(), searchType, searchSort, searchPrice_min, searchPrice_max, searchCountry, searchTagList.joinToString(","), searchSweetness).enqueue(object : retrofit2.Callback<DrinkCoverResponse> {
                    override fun onResponse(
                        call: Call<DrinkCoverResponse>,
                        response: Response<DrinkCoverResponse>
                    ) {
                        val resp = response.body()
                        drinkSearchAdapter?.clearItems()
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

                // 키보드 숨기기
                val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.drinkSearchTopSearchEd.windowToken, 0)

                true
            } else false


        }






        drinkSearchAdapter = DrinkSearchAdapter((drinkSearchList))
        binding.drinkSearchResultRv.adapter = drinkSearchAdapter
        binding.drinkSearchResultRv.layoutManager = GridLayoutManager(context, 2)


    }




    // 필터 버튼 클릭 (주종)
    inner class searchFilterButton_sort(private val sortNum : Int) : View.OnClickListener{
        override fun onClick(v: View?) {
            val tv = v as TextView

            // 기존에 선택 되있는 버튼 해제
            val button_check = searchSort_filter.indexOf(true)
            if(button_check >= 0){
                when(button_check){
                    0 -> {
                        binding.drinkSearchButtonFilterBeerTv.setBackgroundResource(R.drawable.background_button_search)
                        binding.drinkSearchButtonFilterBeerTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

                        binding.drinkSearchFilterCountryCl.visibility = View.GONE
                        binding.drinkSearchFilterTagCl.visibility = View.GONE
                    }
                    1 -> {
                        binding.drinkSearchButtonFilterWineTv.setBackgroundResource(R.drawable.background_button_search)
                        binding.drinkSearchButtonFilterWineTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                    2 -> {
                        binding.drinkSearchButtonFilterTraditionalLiquorTv.setBackgroundResource(R.drawable.background_button_search)
                        binding.drinkSearchButtonFilterTraditionalLiquorTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                    3 -> {
                        binding.drinkSearchButtonFilterWhiskeyTv.setBackgroundResource(R.drawable.background_button_search)
                        binding.drinkSearchButtonFilterWhiskeyTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                    4 -> {
                        binding.drinkSearchButtonFilterLiqueurTv.setBackgroundResource(R.drawable.background_button_search)
                        binding.drinkSearchButtonFilterLiqueurTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                    5 -> {
                        binding.drinkSearchButtonFilterVodkaTv.setBackgroundResource(R.drawable.background_button_search)
                        binding.drinkSearchButtonFilterVodkaTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                }

                searchSort = null
                searchSort_filter[button_check] = false
            }

            // 버튼 클릭
            if(searchSort_filter[sortNum]){
                v.setBackgroundResource(R.drawable.background_button_search)
                tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                searchSort = null
                searchSort_filter[sortNum] = false

            } else {
                v.setBackgroundResource(R.drawable.background_button_search_check)
                tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                searchSort_filter[sortNum] = true

            }

            when(sortNum){
                0 -> {
                    searchSort = "맥주"
                    binding.drinkSearchFilterCountryCl.visibility = View.VISIBLE
                    binding.drinkSearchFilterTagCl.visibility = View.VISIBLE
                }
                1 -> searchSort = "와인"
                2 -> searchSort = "전통주"
                3 -> searchSort = "위스키"
                4 -> searchSort = "리큐르"
                5 -> searchSort = "보드카"
            }


        }


    }


    // 필터 버튼 클릭 (정렬기준)
    inner class searchFilterButton_type(private val typeNum : Int) : View.OnClickListener{
        override fun onClick(v: View?) {
            val tv = v as TextView

            // 기존에 선택 되있는 버튼 해제
            val button_check = searchType_filter.indexOf(true)
            if(button_check >= 0){
                when(button_check){
                    0 -> {
                        binding.drinkSearchButtonFilterNameTv.setBackgroundResource(R.drawable.background_button_search)
                        binding.drinkSearchButtonFilterNameTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                    1 -> {
                        binding.drinkSearchButtonFilterReviewTv.setBackgroundResource(R.drawable.background_button_search)
                        binding.drinkSearchButtonFilterReviewTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                    2 -> {
                        binding.drinkSearchButtonFilterLowPriceTv.setBackgroundResource(R.drawable.background_button_search)
                        binding.drinkSearchButtonFilterLowPriceTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                    3 -> {
                        binding.drinkSearchButtonFilterHighRatingTv.setBackgroundResource(R.drawable.background_button_search)
                        binding.drinkSearchButtonFilterHighRatingTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                }

                searchType = null
                searchType_filter[button_check] = false
            }

            // 버튼 클릭
            if(searchType_filter[typeNum]){
                v.setBackgroundResource(R.drawable.background_button_search)
                tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                searchType = null
                searchType_filter[typeNum] = false

            } else {
                v.setBackgroundResource(R.drawable.background_button_search_check)
                tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                searchType_filter[typeNum] = true

            }

            when(typeNum){
                0 -> searchType = "name"
                1 -> searchType = "review"
                2 -> searchType = "price"
                3 -> searchType = "rating"
            }


        }


    }


    /*
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

     */


    private fun initData(data: ArrayList<DrinkCover>) {
        drinkSearchList.addAll(data)
        drinkSearchAdapter?.notifyDataSetChanged()
    }




}