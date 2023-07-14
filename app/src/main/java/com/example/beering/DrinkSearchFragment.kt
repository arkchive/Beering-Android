package com.example.beering
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beering.databinding.FragmentDrinkSearchBinding
import com.example.naverwebtoon.data.DrinkCover

class DrinkSearchFragmentFragment: Fragment() {
    lateinit var binding: FragmentDrinkSearchBinding

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


        binding.drinkSearchTopCancelTv.setOnClickListener {
            // api로 데이터 받아오는 부분 작성 -> 이거를 버튼 눌렀을때로 변경해야할듯
            val data : ArrayList<DrinkCover> = ArrayList()

            data.add(0,
                DrinkCover("타이거",
                    "Tiger",
                    "Pilsner Urquell Brewery",
                    1,
                    R.drawable.login_request_banner)
            )


            // 받아온 데이터 넣는 부분
            if (data != null) {
                initData(data)
            }

            // 상세 페이지 구현시, 구현
            /*
            drinkSearchAdapter!!.setOnItemClickListener(object : DrinkSearchAdapter.OnItemClickListener {


                override fun onItemClick(webtoonInfo: WebtoonCover) {
                    val webtoonBundle = Bundle()
                    val webtoonDetailFragment = WebtoonDetailFragment()
                    val dataJson = Gson().toJson(webtoonInfo)
                    webtoonBundle.putString("webtoonInfo", dataJson)
                    webtoonDetailFragment.arguments = webtoonBundle
                    parentFragment!!.parentFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, webtoonDetailFragment).commit()
                }


            })

             */
        }





        drinkSearchAdapter = DrinkSearchAdapter((drinkSearchList))
        binding.drinkSearchResultRv.adapter = drinkSearchAdapter
        binding.drinkSearchResultRv.layoutManager = LinearLayoutManager(context)


    }



    private fun initData(data: ArrayList<DrinkCover>) {
        drinkSearchList.addAll(data)
        drinkSearchAdapter?.notifyDataSetChanged()
    }
}