package com.example.beering.feature.auth.join.view

import com.example.beering.R
import com.example.beering.databinding.ActivityJoinTermBinding
import com.example.beering.util.BaseActivity

class TermActivity : BaseActivity<ActivityJoinTermBinding>(ActivityJoinTermBinding::inflate) {
    override fun initAfterBinding() {
        binding.termHeaderCl.toolbarBackIv.setOnClickListener {
            finish()
        }
    }
}