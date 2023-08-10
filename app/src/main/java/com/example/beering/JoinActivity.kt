package com.example.beering

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.beering.api.ApiClient
import com.example.beering.api.JoinApiService
import com.example.beering.api.getRetrofit_sync
import com.example.beering.data.Member
import com.example.beering.data.MemberAgreements
import com.example.beering.data.MemberResponse
import com.example.beering.databinding.ActivityJoinBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class JoinActivity: AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        // api 연결
        val joinService = getRetrofit_sync().create(JoinApiService::class.java)


        // 객체 생성
        val usernameEdit = binding.joinIdEd
        val passwordEdit = binding.joinPasswordEd
        val passwordAgainEdit = binding.joinPasswordAgainEd
        val nicknameEdit = binding.joinNicknameEd

        var idBool:Boolean = false
        var passwordBool:Boolean = false
        var nicknameBool:Boolean = false
        var checkbox1Bool:Boolean = false
        var checkbox2Bool:Boolean = false



        // 메시지 담을 변수
        var username: String = ""
        var password: String = ""
        var passwordAgain: String = ""
        var nickname: String = ""
        var agreementList: MutableList<MemberAgreements> = mutableListOf(
            MemberAgreements("SERVICE", false),
            MemberAgreements("PERSONAL", false),
            MemberAgreements("MARKETING", false)
        )

        // EditText 값 있을 때만 버튼 활성화
        // 아이디
        usernameEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            // 값 변경 시 실행되는 함수
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                // 입력값 담기
                username = usernameEdit.text.toString()

                // 값 유무에 따른 활성화 여부
                if (username.isNotEmpty()) {
                    usernameEdit.setTextColor(
                        ContextCompat.getColor(
                            this@JoinActivity,
                            R.color.beering_black
                        )
                    )
                    binding.joinIdBar.setBackgroundColor(
                        ContextCompat.getColor(
                            this@JoinActivity,
                            R.color.beering_black
                        )
                    )
                    binding.joinIdIv1.setImageResource(R.drawable.ic_delete_dark)
                    binding.joinIdIv21.visibility = View.INVISIBLE
                    binding.joinIdIv22.visibility = View.VISIBLE

                    binding.joinIdIv1.setOnClickListener {
                        binding.joinIdEd.text.clear()
                        username = ""
                    }

                } else {
                    usernameEdit.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.gray01))
                    binding.joinIdBar.setBackgroundColor(
                        ContextCompat.getColor(
                            this@JoinActivity,
                            R.color.gray01
                        )
                    )
                    binding.joinIdIv1.setImageResource(R.drawable.ic_delete_light)
                    binding.joinIdIv21.visibility = View.VISIBLE
                    binding.joinIdIv22.visibility = View.INVISIBLE
                    binding.joinIdNotice.visibility = View.INVISIBLE
                }

                // 아이디 유효성 검사
                // TODO 아이디 이메일 형식 체크
                binding.joinIdIv22.setOnClickListener {
                // api 연결 후 중복 확인
                    joinService.checkUsernameValidate(username).enqueue(object : retrofit2.Callback<MemberResponse> {
                        override fun onResponse(
                            call: Call<MemberResponse>,
                            response: Response<MemberResponse>,
                        ) {
                            val resp = response.body()
                            if(resp!=null && resp.isSuccess) {
                                val responseCode = resp.responseCode
                                Log.i("checkUsernameValidate/SUCCESS", resp.toString())
                                Log.d("ResponseCode", "API Response Code: $responseCode")
                                if( resp.responseCode == 2011 ){
                                    binding.joinIdBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_red))
                                    binding.joinIdNotice.setText("이미 사용하고 있는 아이디예요")
                                    binding.joinIdNotice.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_red))
                                    binding.joinIdNotice.visibility = View.VISIBLE
                                    idBool = false
                                } else {
                                    binding.joinIdBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_green))
                                    binding.joinIdNotice.setText("사용할 수 있는 아이디예요")
                                    binding.joinIdNotice.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_green))
                                    binding.joinIdNotice.visibility = View.VISIBLE
                                    idBool = true
                                }
                            }
                        }
                        override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                            Log.d("APIError", "API Request Failed: ${t.message}", t)
                        }
                    })
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // 비밀번호
        passwordEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                password = passwordEdit.text.toString()

                if (password.isNotEmpty()) {
                    passwordEdit.setTextColor(
                        ContextCompat.getColor(
                            this@JoinActivity,
                            R.color.beering_black
                        )
                    )
                    binding.joinPasswordBar.setBackgroundColor(
                        ContextCompat.getColor(
                            this@JoinActivity,
                            R.color.beering_red
                        )
                    )
                    binding.joinPasswordIv.setImageResource(R.drawable.ic_delete_dark)
                    binding.joinPasswordInvisibleIv.visibility = View.VISIBLE

                    binding.joinPasswordIv.setOnClickListener {
                        binding.joinPasswordEd.text.clear()
                        password = ""
                        validatePasswordAgain(passwordAgain, password)
                    }
                    if(validatePassword(password)) {
                        binding.joinPasswordBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_green))
                        passwordBool = true
                    }
                    if(passwordAgain!="") {
                        validatePasswordAgain(passwordAgain, password)
                    }


                } else {
                    passwordEdit.setTextColor(
                        ContextCompat.getColor(
                            this@JoinActivity,
                            R.color.gray01
                        )
                    )
                    binding.joinPasswordBar.setBackgroundColor(
                        ContextCompat.getColor(
                            this@JoinActivity,
                            R.color.gray01
                        )
                    )
                    binding.joinPasswordIv.setImageResource(R.drawable.ic_delete_light)
                    binding.joinPasswordVisibleIv.visibility = View.INVISIBLE
                    binding.joinPasswordInvisibleIv.visibility = View.INVISIBLE
                }

                // 비밀번호 토글 활성화
                binding.joinPasswordInvisibleIv.setOnClickListener {
                    binding.joinPasswordEd.inputType = InputType.TYPE_CLASS_TEXT
                    binding.joinPasswordInvisibleIv.visibility = View.INVISIBLE
                    binding.joinPasswordVisibleIv.visibility = View.VISIBLE
                }
                binding.joinPasswordVisibleIv.setOnClickListener {
                    binding.joinPasswordEd.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    binding.joinPasswordInvisibleIv.visibility = View.VISIBLE
                    binding.joinPasswordVisibleIv.visibility = View.INVISIBLE
                }

            }

            override fun afterTextChanged(s: Editable?) { }
        })

        // 비밀번호 한번 더 입력
        passwordAgainEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                passwordAgain = passwordAgainEdit.text.toString()

                if (passwordAgain.isNotEmpty()) {
                    passwordAgainEdit.setTextColor(
                        ContextCompat.getColor(
                            this@JoinActivity,
                            R.color.beering_black
                        )
                    )
                    binding.joinPasswordAgainBar.setBackgroundColor(
                        ContextCompat.getColor(
                            this@JoinActivity,
                            R.color.beering_red
                        )
                    )
                    binding.joinPasswordAgainIv.setImageResource(R.drawable.ic_delete_dark)
                    binding.joinPasswordAgainInvisibleIv.visibility = View.VISIBLE

                    binding.joinPasswordAgainIv.setOnClickListener {
                        binding.joinPasswordAgainEd.text.clear()
                        passwordAgain = ""
                    }

                    validatePasswordAgain(passwordAgain, password)

                } else {
                    passwordAgainEdit.setTextColor(
                        ContextCompat.getColor(
                            this@JoinActivity,
                            R.color.gray01
                        )
                    )
                    binding.joinPasswordAgainBar.setBackgroundColor(
                        ContextCompat.getColor(
                            this@JoinActivity,
                            R.color.gray01
                        )
                    )
                    binding.joinPasswordAgainIv.setImageResource(R.drawable.ic_delete_light)
                    binding.joinPasswordAgainVisibleIv.visibility = View.INVISIBLE
                    binding.joinPasswordAgainInvisibleIv.visibility = View.INVISIBLE
                    binding.joinPasswordAgainNotice.visibility = View.INVISIBLE
                }

                binding.joinPasswordAgainInvisibleIv.setOnClickListener {
                    binding.joinPasswordAgainEd.inputType = InputType.TYPE_CLASS_TEXT
                    binding.joinPasswordAgainInvisibleIv.visibility = View.INVISIBLE
                    binding.joinPasswordAgainVisibleIv.visibility = View.VISIBLE
                }
                binding.joinPasswordAgainVisibleIv.setOnClickListener {
                    binding.joinPasswordAgainEd.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    binding.joinPasswordAgainInvisibleIv.visibility = View.VISIBLE
                    binding.joinPasswordAgainVisibleIv.visibility = View.INVISIBLE
                }

            }

            override fun afterTextChanged(s: Editable?) {}

        })

        // 닉네임
        nicknameEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                nickname = nicknameEdit.text.toString()

                if (nickname.isNotEmpty()) {
                    nicknameEdit.setTextColor(
                        ContextCompat.getColor(
                            this@JoinActivity,
                            R.color.beering_black
                        )
                    )
                    binding.joinNicknameBar.setBackgroundColor(
                        ContextCompat.getColor(
                            this@JoinActivity,
                            R.color.beering_black
                        )
                    )
                    binding.joinNicknameIv1.setImageResource(R.drawable.ic_delete_dark)

                    binding.joinNicknameIv1.setOnClickListener {
                        binding.joinNicknameEd.text.clear()
                        nickname = ""
                    }

                    // 닉네임 유효성 검사
                    // 닉네임이 영어, 한글 문자, 그리고 숫자로만 이루어져 있는지 확인
                    val containsValidCharacters = nickname.matches(Regex("[a-zA-Zㄱ-ㅎ가-힣0-9]+"))
                    if (containsValidCharacters) {
                        binding.conditionText.setTextColor(
                            ContextCompat.getColor(
                                this@JoinActivity,
                                R.color.beering_black
                            )
                        )
                        binding.check5.setImageResource(R.drawable.ic_check_dark)
                    } else {
                        binding.conditionText.setTextColor(
                            ContextCompat.getColor(
                                this@JoinActivity,
                                R.color.gray01
                            )
                        )
                        binding.check5.setImageResource(R.drawable.ic_check_light)
                    }

                    // 닉네임의 길이가 1에서 10자 사이인지 확인
                    val isLengthValid = nickname.length in 1..10
                    if (isLengthValid) {
                        binding.conditionLength2.setTextColor(
                            ContextCompat.getColor(
                                this@JoinActivity,
                                R.color.beering_black
                            )
                        )
                        binding.check6.setImageResource(R.drawable.ic_check_dark)
                    } else {
                        binding.conditionLength2.setTextColor(
                            ContextCompat.getColor(
                                this@JoinActivity,
                                R.color.gray01
                            )
                        )
                        binding.check6.setImageResource(R.drawable.ic_check_light)
                    }

                    if (containsValidCharacters && isLengthValid) {
                        binding.joinNicknameIv21.visibility = View.INVISIBLE
                        binding.joinNicknameIv22.visibility = View.VISIBLE
                    }else {
                        binding.joinNicknameIv21.visibility = View.VISIBLE
                        binding.joinNicknameIv22.visibility = View.INVISIBLE
                    }

//                  닉네임 중복체크
                    binding.joinNicknameIv22.setOnClickListener {
                        joinService.checkNicknameValidate(nickname).enqueue(object : retrofit2.Callback<MemberResponse> {
                            override fun onResponse(
                                call: Call<MemberResponse>,
                                response: Response<MemberResponse>,
                            ) {
                                val resp = response.body()
                                if(resp?.isSuccess == true){
                                    val responseCode = resp.responseCode
                                    Log.d("ResponseCode", "API Response Code: $responseCode")

                                    if(resp.responseCode == 2012 ){
                                        binding.joinNicknameBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_red))
                                        binding.joinNicknameNotice.setText("이미 사용하고 있는 닉네임이에요")
                                        binding.joinNicknameNotice.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_red))
                                        binding.conditionText.visibility = View.INVISIBLE
                                        binding.conditionLength2.visibility = View.INVISIBLE
                                        binding.check5.visibility = View.INVISIBLE
                                        binding.check6.visibility = View.INVISIBLE
                                        binding.joinNicknameNotice.visibility = View.VISIBLE
                                        nicknameBool = false
                                    } else {
                                        binding.joinNicknameBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_green))
                                        binding.joinNicknameNotice.setText("사용할 수 있는 닉네임이에요")
                                        binding.joinNicknameNotice.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.beering_green))
                                        binding.conditionText.visibility = View.INVISIBLE
                                        binding.conditionLength2.visibility = View.INVISIBLE
                                        binding.check5.visibility = View.INVISIBLE
                                        binding.check6.visibility = View.INVISIBLE
                                        binding.joinNicknameNotice.visibility = View.VISIBLE
                                        nicknameBool = true
                                    }
                                }
                            }
                            override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                                Log.e("APIError", "API Request Failed: ${t.message}", t)
                            }
                        })
                    }
                } else {
                    nicknameEdit.setTextColor(
                        ContextCompat.getColor(
                            this@JoinActivity,
                            R.color.gray01
                        )
                    )
                    binding.joinNicknameBar.setBackgroundColor(
                        ContextCompat.getColor(
                            this@JoinActivity,
                            R.color.gray01
                        )
                    )
                    binding.joinNicknameIv1.setImageResource(R.drawable.ic_delete_light)
                    binding.conditionText.setTextColor(ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.gray01))
                    binding.check5.setImageResource(R.drawable.ic_check_light)
                    binding.conditionLength2.setTextColor(ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.gray01))
                    binding.check6.setImageResource(R.drawable.ic_check_light)
                    binding.joinNicknameIv21.visibility = View.VISIBLE
                    binding.joinNicknameIv22.visibility = View.INVISIBLE
                }

            }
            override fun afterTextChanged(s: Editable?) {}
        })


        // 이용약관 동의 체크
        binding.checkboxTerm1On.setOnClickListener {
            binding.checkboxTerm1On.visibility = View.INVISIBLE
            binding.checkboxTerm1Off.visibility = View.VISIBLE
            checkbox1Bool = true
            val serviceAgreement = agreementList.find {it.name == "SERVICE"}
            serviceAgreement?.isAgreed = true
        }
        binding.checkboxTerm1Off.setOnClickListener {
            binding.checkboxTerm1Off.visibility = View.INVISIBLE
            binding.checkboxTerm1On.visibility = View.VISIBLE
            checkbox1Bool = false
            val serviceAgreement = agreementList.find {it.name == "SERVICE"}
            serviceAgreement?.isAgreed = false
        }

        binding.checkboxTerm2On.setOnClickListener {
            binding.checkboxTerm2On.visibility = View.INVISIBLE
            binding.checkboxTerm2Off.visibility = View.VISIBLE
            checkbox2Bool = true
            val personalAgreement = agreementList.find {it.name == "PERSONAL"}
            personalAgreement?.isAgreed = true
        }
        binding.checkboxTerm2Off.setOnClickListener {
            binding.checkboxTerm2Off.visibility = View.INVISIBLE
            binding.checkboxTerm2On.visibility = View.VISIBLE
            checkbox2Bool = false
            val personalAgreement = agreementList.find {it.name == "PERSONAL"}
            personalAgreement?.isAgreed = false
        }

        binding.checkboxTerm3On.setOnClickListener {
            binding.checkboxTerm3On.visibility = View.INVISIBLE
            binding.checkboxTerm3Off.visibility = View.VISIBLE
            val marketingAgreement = agreementList.find {it.name == "MARKETING"}
            marketingAgreement?.isAgreed = true
        }
        binding.checkboxTerm3Off.setOnClickListener {
            binding.checkboxTerm3Off.visibility = View.INVISIBLE
            binding.checkboxTerm3On.visibility = View.VISIBLE
            val marketingAgreement = agreementList.find {it.name == "MARKETING"}
            marketingAgreement?.isAgreed = false
        }


        // 이용약관 다이얼로그
        binding.viewMoreTerm1.setOnClickListener {
            val term1Dialog = Term1Dialog()
            term1Dialog.show(supportFragmentManager,"term1Dialog")
        }
        binding.viewMoreTerm2.setOnClickListener {
            val term2Dialog = Term2Dialog()
            term2Dialog.show(supportFragmentManager,"term2Dialog")
        }
        binding.viewMoreTerm3.setOnClickListener {
            val term3Dialog = Term3Dialog()
            term3Dialog.show(supportFragmentManager,"term3Dialog")
        }

        // 회원가입 버튼 활성화 그리고 api 연결
        if(idBool && passwordBool && nicknameBool && checkbox1Bool && checkbox2Bool) {
           binding.joinBtnLight.visibility = View.INVISIBLE
           binding.joinBtnDark.visibility = View.VISIBLE

           binding.joinBtnDark.setOnClickListener {
               val member = Member(username, password, nickname, agreementList)

               val call = joinService.signUp(member)

               call.enqueue(object : retrofit2.Callback<MemberResponse> {
                   override fun onResponse(call: Call<MemberResponse>, response: Response<MemberResponse>){
                       if(response.isSuccessful) {
                           val memberResponse = response.body()
                           if(memberResponse?.isSuccess == true){
                               val intent = Intent(this@JoinActivity, LoginActivity::class.java)
                               startActivity(intent)
                           } else {
                                Toast.makeText(this@JoinActivity, "로그인을 실패하였습니다.",Toast.LENGTH_SHORT)
                           }
                       }
                   }

                   override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                       Toast.makeText(this@JoinActivity, "서버에 요청을 실패하였습니다.",Toast.LENGTH_SHORT)
                   }
               })
           }
        }

    }

    // 비밀번호 유효성 검사
    fun validatePassword(password: String): Boolean {
        // 비밀번호가 영문자를 포함하는지 확인
        val containsEnglishChars = password.matches(Regex(".*[a-zA-Z].*"))
        if (containsEnglishChars) {
            binding.conditionEng.setTextColor(
                ContextCompat.getColor(
                    this@JoinActivity,
                    R.color.beering_black
                )
            )
            binding.check1.setImageResource(R.drawable.ic_check_dark)
        } else {
            binding.conditionEng.setTextColor(
                ContextCompat.getColor(
                    this@JoinActivity,
                    R.color.gray01
                )
            )
            binding.check1.setImageResource(R.drawable.ic_check_light)
        }

        // 비밀번호가 특수문자를 포함하는지 확인
        val containsSpecialChars = password.matches(Regex(".*[!@#\\\$%].*"))
        if (containsSpecialChars) {
            binding.conditionCharacter.setTextColor(
                ContextCompat.getColor(
                    this@JoinActivity,
                    R.color.beering_black
                )
            )
            binding.check2.setImageResource(R.drawable.ic_check_dark)
        } else {
            binding.conditionCharacter.setTextColor(
                ContextCompat.getColor(
                    this@JoinActivity,
                    R.color.gray01
                )
            )
            binding.check2.setImageResource(R.drawable.ic_check_light)
        }

        // 비밀번호가 숫자를 포함하는지 확인
        val containsNumbers = password.matches(Regex(".*[0-9].*"))
        if (containsNumbers) {
            binding.conditionNum.setTextColor(
                ContextCompat.getColor(
                    this@JoinActivity,
                    R.color.beering_black
                )
            )
            binding.check3.setImageResource(R.drawable.ic_check_dark)
        } else {
            binding.conditionNum.setTextColor(
                ContextCompat.getColor(
                    this@JoinActivity,
                    R.color.gray01
                )
            )
            binding.check3.setImageResource(R.drawable.ic_check_light)
        }

        // 비밀번호의 길이가 8자에서 20자 사이인지 확인
        val isLengthValid = password.length in 8..20
        if (isLengthValid) {
            binding.conditionLength.setTextColor(
                ContextCompat.getColor(
                    this@JoinActivity,
                    R.color.beering_black
                )
            )
            binding.check4.setImageResource(R.drawable.ic_check_dark)
        } else {
            binding.conditionLength.setTextColor(
                ContextCompat.getColor(
                    this@JoinActivity,
                    R.color.gray01
                )
            )
            binding.check4.setImageResource(R.drawable.ic_check_light)
        }

        return containsEnglishChars && containsSpecialChars && containsNumbers && isLengthValid
    }

    fun validatePasswordAgain(passwordAgain:String, password:String):Boolean {
        // 비밀번호 일치 조건문
        if (passwordAgain == password) {
            binding.joinPasswordAgainBar.setBackgroundColor(
                ContextCompat.getColor(
                    this@JoinActivity,
                    R.color.beering_green
                )
            )
            binding.joinPasswordAgainNotice.setText("비밀번호가 일치해요")
            binding.joinPasswordAgainNotice.setTextColor(
                ContextCompat.getColor(
                    this@JoinActivity,
                    R.color.beering_green
                )
            )
            binding.joinPasswordAgainNotice.visibility = View.VISIBLE

        } else {
            binding.joinPasswordAgainBar.setBackgroundColor(
                ContextCompat.getColor(
                    this@JoinActivity,
                    R.color.beering_red
                )
            )
            binding.joinPasswordAgainNotice.setText("비밀번호가 일치하지 않아요")
            binding.joinPasswordAgainNotice.setTextColor(
                ContextCompat.getColor(
                    this@JoinActivity,
                    R.color.beering_red
                )
            )
            binding.joinPasswordAgainNotice.visibility = View.VISIBLE
        }

        return (passwordAgain == password)
    }

}