package com.example.beering

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
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
        val passwordAgainEdit = binding.joinPasswordAgainEd
        val nicknameEdit = binding.joinNicknameEd
        val joinBtn = binding.joinBtn


        // 메시지 담을 변수
        var id: String = ""
        var password: String = ""
        var passwordAgain: String = ""
        var nickname: String = ""

        // 버튼 비활성화
        joinBtn.isEnabled = false

        // EditText 값 있을때만 버튼 활성화
        // 아이디
        idEdit.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            // 값 변경 시 실행되는 함수
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                // 입력값 담기
                id = idEdit.text.toString()

                // 값 유무에 따른 활성화 여부
                if (id.isNotEmpty()) {
                    idEdit.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_black))
                    binding.joinIdBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_black))
                    binding.joinIdIv1.setImageResource(R.drawable.ic_delete_dark)
                    binding.joinIdIv2.setImageResource(R.drawable.ic_duplicate_dark)

                    binding.joinIdIv1.setOnClickListener {
                        binding.joinIdEd.text.clear()
                        id = ""
                    }

                    // 중복체크 클릭 시
                    binding.joinIdIv2.setOnClickListener {
                        //  TODO api 연결 후 중복 확인
//                        if( 중복하면 ){
//                            binding.joinIdBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_red))
//                            binding.joinIdNotice.setText("이미 사용하고 있는 아이디예요")
//                            binding.joinIdNotice.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_red))
//                            binding.joinIdNotice.visibility = View.VISIBLE
//                        } else {
//                            binding.joinIdBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_green))
//                            binding.joinIdNotice.setText("사용할 수 있는 아이디예요")
//                            binding.joinIdNotice.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_green))
//                            binding.joinIdNotice.visibility = View.VISIBLE
//                        }
                    }

                } else {
                    idEdit.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.gray01))
                    binding.joinIdBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.gray01))
                    binding.joinIdIv1.setImageResource(R.drawable.ic_delete_light)
                    binding.joinIdIv2.setImageResource(R.drawable.ic_duplicate_light)
                    binding.joinIdNotice.visibility = View.INVISIBLE
                }

            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // 비밀번호
        passwordEdit.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                password = passwordEdit.text.toString()

                if(password.isNotEmpty()) {
                    passwordEdit.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_black))
                    binding.joinPasswordBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_red))
                    binding.joinPasswordIv.setImageResource(R.drawable.ic_delete_dark)
                    binding.joinPasswordInvisibleIv.visibility = View.VISIBLE

                    binding.joinPasswordIv.setOnClickListener {
                        binding.joinPasswordEd.text.clear()
                        password = ""
                    }

                } else {
                    passwordEdit.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.gray01))
                    binding.joinPasswordBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.gray01))
                    binding.joinPasswordIv.setImageResource(R.drawable.ic_delete_light)
                    binding.joinPasswordVisibleIv.visibility = View.INVISIBLE
                    binding.joinPasswordInvisibleIv.visibility = View.INVISIBLE
                }

                // TODO 비밀번호 조건 충족 체크

                // 비밀번호 토글 활성화
                binding.joinPasswordInvisibleIv.setOnClickListener {
                    binding.joinPasswordEd.inputType = InputType.TYPE_CLASS_TEXT
                    binding.joinPasswordInvisibleIv.visibility = View.INVISIBLE
                    binding.joinPasswordVisibleIv.visibility = View.VISIBLE
                }
                binding.joinPasswordVisibleIv.setOnClickListener {
                    binding.joinPasswordEd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    binding.joinPasswordInvisibleIv.visibility = View.VISIBLE
                    binding.joinPasswordVisibleIv.visibility = View.INVISIBLE
                }

            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // 비밀번호 한번 더 입력
        passwordAgainEdit.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                passwordAgain = passwordAgainEdit.text.toString()

                if(passwordAgain.isNotEmpty()) {
                    passwordAgainEdit.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_black))
                    binding.joinPasswordAgainBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_red))
                    binding.joinPasswordAgainIv.setImageResource(R.drawable.ic_delete_dark)
                    binding.joinPasswordAgainInvisibleIv.visibility = View.VISIBLE

                    binding.joinPasswordAgainIv.setOnClickListener {
                        binding.joinPasswordAgainEd.text.clear()
                        passwordAgain = ""
                    }

                    // 비밀번호 일치 조건문
                    if(passwordAgain == password){
                        binding.joinPasswordAgainBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_green))
                        binding.joinPasswordAgainNotice.setText("비밀번호가 일치해요")
                        binding.joinPasswordAgainNotice.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_green))
                        binding.joinPasswordAgainNotice.visibility = View.VISIBLE

                    } else {
                        binding.joinPasswordAgainBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_red))
                        binding.joinPasswordAgainNotice.setText("비밀번호가 일치하지 않아요")
                        binding.joinPasswordAgainNotice.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_red))
                        binding.joinPasswordAgainNotice.visibility = View.VISIBLE
                    }

                } else {
                    passwordAgainEdit.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.gray01))
                    binding.joinPasswordAgainBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.gray01))
                    binding.joinPasswordAgainIv.setImageResource(R.drawable.ic_delete_light)
                    binding.joinPasswordAgainVisibleIv.visibility = View.INVISIBLE
                    binding.joinPasswordAgainInvisibleIv.visibility = View.INVISIBLE
                    binding.joinPasswordAgainNotice.visibility = View.INVISIBLE
                }

            }

            override fun afterTextChanged(s: Editable?) {}

        })

        // 닉네임
        nicknameEdit.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                nickname = nicknameEdit.text.toString()

                if(nickname.isNotEmpty()) {
                    nicknameEdit.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_black))
                    binding.joinNicknameBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_black))
                    binding.joinNicknameIv1.setImageResource(R.drawable.ic_delete_dark)

                    binding.joinNicknameIv1.setOnClickListener {
                        binding.joinNicknameEd.text.clear()
                        nickname = ""
                    }

                    // TODO 닉네임 조건 두개 충족 시 중복체크 버튼 활성화

                    // TODO 닉네임 중복체크 클릭 시
//                    binding.joinNicknameIv2.setOnClickListener {
//                        if( 중복하면 ){
//                            binding.joinNicknameBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_red))
//                            binding.joinNicknameNotice.setText("이미 사용하고 있는 닉네임이에요")
//                            binding.joinNicknameNotice.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_red))
//                            binding.joinNicknameNotice.visibility = View.VISIBLE
//                        } else {
//                            binding.joinNicknameBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_green))
//                            binding.joinNicknameNotice.setText("사용할 수 있는 닉네임이에요")
//                            binding.joinNicknameNotice.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_green))
//                            binding.joinNicknameNotice.visibility = View.VISIBLE
//                        }
//                    }


                } else {
                    nicknameEdit.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.gray01))
                    binding.joinNicknameBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.gray01))
                    binding.joinNicknameIv1.setImageResource(R.drawable.ic_delete_light)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        // 이용약관 동의 체크
        binding.checkboxTerm1On.setOnClickListener {
            binding.checkboxTerm1On.visibility = View.INVISIBLE
            binding.checkboxTerm1Off.visibility = View.VISIBLE
        }
        binding.checkboxTerm1Off.setOnClickListener {
            binding.checkboxTerm1Off.visibility = View.INVISIBLE
            binding.checkboxTerm1On.visibility = View.VISIBLE
        }

        binding.checkboxTerm2On.setOnClickListener {
            binding.checkboxTerm2On.visibility = View.INVISIBLE
            binding.checkboxTerm2Off.visibility = View.VISIBLE
        }
        binding.checkboxTerm2Off.setOnClickListener {
            binding.checkboxTerm2Off.visibility = View.INVISIBLE
            binding.checkboxTerm2On.visibility = View.VISIBLE
        }

        binding.checkboxTerm3On.setOnClickListener {
            binding.checkboxTerm3On.visibility = View.INVISIBLE
            binding.checkboxTerm3Off.visibility = View.VISIBLE
        }
        binding.checkboxTerm3Off.setOnClickListener {
            binding.checkboxTerm3Off.visibility = View.INVISIBLE
            binding.checkboxTerm3On.visibility = View.VISIBLE
        }


        // TODO 이용약관 내용 정해지면, 각 dialog에 맞는 내용 채워주는 코드 작성해야함.
        binding.viewMoreTerm1.setOnClickListener {

        }
        binding.viewMoreTerm2.setOnClickListener {

        }
        binding.viewMoreTerm3.setOnClickListener {

        }

        // TODO 회원가입 버튼 활성화



    }
}