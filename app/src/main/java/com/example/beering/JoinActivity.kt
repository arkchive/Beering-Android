package com.example.beering

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.beering.databinding.ActivityJoinBinding

class JoinActivity: AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        // 객체 생성
        val idEdit = binding.joinIdEd
        val passwordEdit = binding.joinPasswordEd
        val nicknameEdit = binding.joinNicknameEd
        val joinBtn = binding.joinBtn


        // 메시지 담을 변수
        var id: String = ""
        var password: String = ""
        var nickname: String = ""

        //버튼 비활성화
        joinBtn.isEnabled = false

        //EditText 값 있을때만 버튼 활성화
        idEdit.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            // 값 변경 시 실행되는 함수
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                // 입력값 담기
                id = idEdit.text.toString()
                password = passwordEdit.text.toString()
                nickname = nicknameEdit.text.toString()

                // 값 유무에 따른 활성화 여부
                // 아이디
                if (id.isNotEmpty()) {
                    idEdit.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_black))
                    binding.joinIdBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_black))
                    binding.joinIdIv1.setImageResource(R.drawable.ic_delete_dark)
                    binding.joinIdIv2.setImageResource(R.drawable.ic_duplicate_dark)

                    binding.joinIdIv2.setOnClickListener {
                        // api 연결 후 중복 확인
                    }

                } else {
                    idEdit.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.gray01))
                    binding.joinIdBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.gray01))
                    binding.joinIdIv1.setImageResource(R.drawable.ic_delete_light)
                    binding.joinIdIv2.setImageResource(R.drawable.ic_duplicate_light)
                }

                // 닉네임
                if(nickname.isNotEmpty()) {
                    nicknameEdit.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_black))
                    binding.joinNicknameBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_black))
                    binding.joinNicknameIv1.setImageResource(R.drawable.ic_delete_dark)
                } else {
                    nicknameEdit.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.gray01))
                    binding.joinNicknameBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.gray01))
                    binding.joinNicknameIv1.setImageResource(R.drawable.ic_delete_light)
                }

            }

            override fun afterTextChanged(s: Editable?) {}

        })




        // 이용약관 내용 정해지면, 각 dialog에 맞는 내용 채워주는 코드 작성해야함.





    }
}