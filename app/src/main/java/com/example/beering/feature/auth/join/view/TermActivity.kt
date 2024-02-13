package com.example.beering.feature.auth.join.view

import android.text.Html
import androidx.activity.OnBackPressedCallback
import com.example.beering.R
import com.example.beering.databinding.ActivityJoinTermBinding
import com.example.beering.util.BaseActivity
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class TermActivity : BaseActivity<ActivityJoinTermBinding>(ActivityJoinTermBinding::inflate) {
    override fun initAfterBinding() {
        // 뒤로가기 버튼 로직 재정의
        val onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(binding.termSlidingLayout.panelState == SlidingUpPanelLayout.PanelState.EXPANDED){
                    closePanel()
                } else {
                    finish()
                }
            }
        }

        binding.termSlidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        binding.termHeaderCl.toolbarBackIv.setOnClickListener {
            finish()
        }
        // 각 이용약관 선택했을 때 해당 약관내용 HTML 파일을 읽어서 문자열로 변환
        binding.termFirstLl.setOnClickListener{
            val htmlContent = readHtmlFile(R.raw.term_first_script)
            binding.termScriptTv.text = Html.fromHtml(htmlContent)
            binding.termSlidingLayout.apply{
                panelState = SlidingUpPanelLayout.PanelState.EXPANDED
                isTouchEnabled = false
            }
        }
        binding.termSecondLl.setOnClickListener{
            val htmlContent = readHtmlFile(R.raw.term_second_script)
            binding.termScriptTv.text = Html.fromHtml(htmlContent)
            binding.termSlidingLayout.apply{
                panelState = SlidingUpPanelLayout.PanelState.EXPANDED
                isTouchEnabled = false
            }
        }
        binding.termThirdLl.setOnClickListener{
            val htmlContent = readHtmlFile(R.raw.term_third_script)
            binding.termScriptTv.text = Html.fromHtml(htmlContent)
            binding.termSlidingLayout.apply{
                panelState = SlidingUpPanelLayout.PanelState.EXPANDED
                isTouchEnabled = false
            }
        }
        binding.termAgreeIv.setOnClickListener {
            // TODO : ViewModel에 체크로직 구현
            onBackPressedCallback.handleOnBackPressed()
        }

        // 뒤로가기 버튼 로직 재정의
        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

    }

    private fun closePanel(){
        binding.termSlidingLayout.apply{
            panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            isTouchEnabled = true
        }
        binding.termScriptSv.scrollTo(0, 0)
    }

    private fun readHtmlFile(resourceId: Int): String {
        val inputStream = resources.openRawResource(resourceId)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?
        try {
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
                stringBuilder.append("\n") // 줄바꿈 추가
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}