package com.example.beering.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.WindowCompat
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T: ViewBinding>(private val inflate: (LayoutInflater) -> T): AppCompatActivity() {
    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarTransparent()
        // 상태 바, 네비게이션 높이 만큼 padding 주기
        binding.root.setPadding(
            0,
            this.statusBarHeight(),
            0,
            this.navigationHeight()
        )
        initAfterBinding()

    }

    protected abstract fun initAfterBinding()

}
