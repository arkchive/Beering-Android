package com.example.beering.feature.auth.join.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.beering.feature.auth.login.LoginActivity
import com.example.beering.R
import com.example.beering.feature.auth.join.term.Term1Dialog
import com.example.beering.feature.auth.join.term.Term2Dialog
import com.example.beering.feature.auth.join.term.Term3Dialog
import com.example.beering.util.getRetrofit_sync
import com.example.beering.databinding.ActivityJoinBinding
import com.example.beering.feature.auth.join.JoinApiService
import com.example.beering.feature.auth.join.Member
import com.example.beering.feature.auth.join.MemberAgreements
import com.example.beering.feature.auth.join.MemberResponse
import retrofit2.Call
import retrofit2.Response

class JoinActivity: AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding
    private val joinViewModel : JoinViewModel by viewModels { JoinViewModel.Factory }


    var idBool:Boolean = false
    var passwordBool:Boolean = false
    var nicknameBool:Boolean = false
    var checkbox1Bool:Boolean = false
    var checkbox2Bool:Boolean = false

    // api 연결
    val joinService = getRetrofit_sync().create(JoinApiService::class.java)

    // 메시지 담을 변수
    var agreementList: MutableList<MemberAgreements> = mutableListOf(
        MemberAgreements("SERVICE", false),
        MemberAgreements("PERSONAL", false),
        MemberAgreements("MARKETING", false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        // 객체 생성
        val userIdEdit = binding.joinIdEd
        val passwordEdit = binding.joinPasswordEd
        val passwordAgainEdit = binding.joinPasswordAgainEd
        val nicknameEdit = binding.joinNicknameEd

        // ViewModel - Observer 초기화
        joinViewModel.userId.observe(this, Observer { userId ->
            // 이곳에서 userId가 변경될 때마다 실행되는 코드 작성
            if(userId.isNotEmpty()){
                userIdEdit.setTextColor(
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
                binding.joinIdIv21.visibility = View.GONE
                binding.joinIdIv22.visibility = View.VISIBLE

                binding.joinIdIv1.setOnClickListener {
                    binding.joinIdEd.text.clear()
                    joinViewModel.setUserId("")
                }
            } else {
                userIdEdit.setTextColor(ContextCompat.getColor(this@JoinActivity,
                    R.color.gray01
                ))
                binding.joinIdBar.setBackgroundColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.gray01
                    )
                )
                binding.joinIdIv1.setImageResource(R.drawable.ic_delete_light)
                binding.joinIdIv21.visibility = View.VISIBLE
                binding.joinIdIv22.visibility = View.GONE
                binding.joinIdNotice.visibility = View.GONE
            }
        })
        joinViewModel.password.observe(this, Observer {
            if (it.isNotEmpty()) {
                validatePasswordAgain(joinViewModel.passwordAgain.value!!, it)
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
                binding.joinPasswordVisibleIv.visibility = View.GONE
                binding.joinPasswordInvisibleIv.visibility = View.GONE
            }
        })
        joinViewModel.passwordAgain.observe(this, Observer{
            if (it.isNotEmpty()) {
                validatePasswordAgain(it, joinViewModel.password.value!!)
                passwordAgainEdit.setTextColor(
                    ContextCompat.getColor(
                        this@JoinActivity,
                        R.color.beering_black
                    )
                )
                binding.joinPasswordAgainIv.setImageResource(R.drawable.ic_delete_dark)
                binding.joinPasswordAgainInvisibleIv.visibility = View.VISIBLE

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
                binding.joinPasswordAgainVisibleIv.visibility = View.GONE
                binding.joinPasswordAgainInvisibleIv.visibility = View.GONE
                binding.joinPasswordAgainNotice.visibility = View.GONE
            }
        })
        joinViewModel.name.observe(this, Observer{
            if (it.isNotEmpty()) {
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

//              닉네임 중복체크
                binding.joinNicknameIv22.setOnClickListener {
                    joinService.checkNicknameValidate(joinViewModel.name.value!!).enqueue(object : retrofit2.Callback<MemberResponse> {
                        override fun onResponse(
                            call: Call<MemberResponse>,
                            response: Response<MemberResponse>,
                        ) {
                            val resp = response.body()
                            if(resp?.isSuccess == true){
                                val responseCode = resp.responseCode
                                Log.d("ResponseCode", "API Response Code: $responseCode")

                                if(resp.responseCode == 2012 ){
                                    binding.joinNicknameBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity,
                                        R.color.beering_red
                                    ))
                                    binding.joinNicknameNotice.setText("이미 사용하고 있는 닉네임이에요")
                                    binding.joinNicknameNotice.setTextColor(ContextCompat.getColor(this@JoinActivity,
                                        R.color.beering_red
                                    ))
                                    binding.conditionText.visibility = View.GONE
                                    binding.conditionLength2.visibility = View.GONE
                                    binding.check5.visibility = View.GONE
                                    binding.check6.visibility = View.GONE
                                    binding.joinNicknameNotice.visibility = View.VISIBLE
                                    nicknameBool = false
                                } else {
                                    binding.joinNicknameBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity,
                                        R.color.beering_green
                                    ))
                                    binding.joinNicknameNotice.setText("사용할 수 있는 닉네임이에요")
                                    binding.joinNicknameNotice.setTextColor(ContextCompat.getColor(this@JoinActivity,
                                        R.color.beering_green
                                    ))
                                    binding.conditionText.visibility = View.GONE
                                    binding.conditionLength2.visibility = View.GONE
                                    binding.check5.visibility = View.GONE
                                    binding.check6.visibility = View.GONE
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
                    R.color.gray01
                ))
                binding.check5.setImageResource(R.drawable.ic_check_light)
                binding.conditionLength2.setTextColor(ContextCompat.getColor(
                    this@JoinActivity,
                    R.color.gray01
                ))
                binding.check6.setImageResource(R.drawable.ic_check_light)
                binding.joinNicknameIv21.visibility = View.VISIBLE
                binding.joinNicknameIv22.visibility = View.GONE
            }
        })
        joinViewModel.nicknameValidation.observe(this, Observer{
            // 닉네임이 영어, 한글 문자, 그리고 숫자로만 이루어져 있는지 확인
            if (it.characters) {
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
            if (it.length) {
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
            // 닉네임이 유효한지 확인
            if (it.valid) {
                binding.joinNicknameIv21.visibility = View.GONE
                binding.joinNicknameIv22.visibility = View.VISIBLE
            }else {
                binding.joinNicknameIv21.visibility = View.VISIBLE
                binding.joinNicknameIv22.visibility = View.GONE
            }
        })  // 닉네임 유효성 검사
        joinViewModel.pwValidation.observe(this, Observer{
            // 비밀번호가 영문자를 포함하는지 확인
            if (it.englishChars) {
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
            if (it.specialChars) {
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
            if (it.numbers) {
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
            if (it.length) {
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

            // 비밀번호가 유효한지 확인
            if(joinViewModel.pwValidation.value!!.valid) {
                binding.joinPasswordBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity,
                    R.color.beering_green
                ))
                passwordBool = true
            }
        })  // 패스워드 유효성 검사

        // EditText 값 있을 때만 버튼 활성화
        // 아이디
        userIdEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            // 값 변경 시 실행되는 함수
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                // 입력값 담기
                joinViewModel.setUserId(userIdEdit.text.toString())

                // 아이디 유효성 검사
                // TODO 아이디 이메일 형식 체크
                binding.joinIdIv22.setOnClickListener {
                // api 연결 후 중복 확인
                    val cleanEmail = joinViewModel.userId.value!!.trim()
                    Log.d("userId", cleanEmail)
                    joinService.checkUserIdValidate(cleanEmail).enqueue(object : retrofit2.Callback<MemberResponse> {
                        override fun onResponse(
                            call: Call<MemberResponse>,
                            response: Response<MemberResponse>,
                        ) {
                            val resp = response.body()
                            if(resp!=null && resp.isSuccess) {
                                val responseCode = resp.responseCode
                                Log.i("checkUserIdValidate/SUCCESS", resp.toString())
                                Log.d("ResponseCode", "API Response Code: $responseCode")
                                if( resp.responseCode == 2011 ){
                                    binding.joinIdBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity,
                                        R.color.beering_red
                                    ))
                                    binding.joinIdNotice.setText("이미 사용하고 있는 아이디예요")
                                    binding.joinIdNotice.setTextColor(ContextCompat.getColor(this@JoinActivity,
                                        R.color.beering_red
                                    ))
                                    binding.joinIdNotice.visibility = View.VISIBLE
                                    idBool = false
                                } else {
                                    binding.joinIdBar.setBackgroundColor(ContextCompat.getColor(this@JoinActivity,
                                        R.color.beering_green
                                    ))
                                    binding.joinIdNotice.setText("사용할 수 있는 아이디예요")
                                    binding.joinIdNotice.setTextColor(ContextCompat.getColor(this@JoinActivity,
                                        R.color.beering_green
                                    ))
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
                joinViewModel.setPassword(passwordEdit.text.toString())
            }

            override fun afterTextChanged(s: Editable?) { }
        })
        // 비밀번호 토글 활성화
        binding.joinPasswordInvisibleIv.setOnClickListener {
            binding.joinPasswordEd.inputType = InputType.TYPE_CLASS_TEXT
            binding.joinPasswordInvisibleIv.visibility = View.GONE
            binding.joinPasswordVisibleIv.visibility = View.VISIBLE
        }
        binding.joinPasswordVisibleIv.setOnClickListener {
            binding.joinPasswordEd.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.joinPasswordInvisibleIv.visibility = View.VISIBLE
            binding.joinPasswordVisibleIv.visibility = View.GONE
        }
        binding.joinPasswordIv.setOnClickListener {
            binding.joinPasswordEd.text.clear()
            joinViewModel.setPassword("")
        }

        // 비밀번호 한번 더 입력
        passwordAgainEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                joinViewModel.setPasswordAgain(passwordAgainEdit.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        // 비밀번호 확인 x버튼
        binding.joinPasswordAgainIv.setOnClickListener {
            binding.joinPasswordAgainEd.text.clear()
            joinViewModel.setPasswordAgain("")
        }
        // 비밀번호 확인 눈 버튼
        binding.joinPasswordAgainInvisibleIv.setOnClickListener {
            binding.joinPasswordAgainEd.inputType = InputType.TYPE_CLASS_TEXT
            binding.joinPasswordAgainInvisibleIv.visibility = View.GONE
            binding.joinPasswordAgainVisibleIv.visibility = View.VISIBLE
        }
        binding.joinPasswordAgainVisibleIv.setOnClickListener {
            binding.joinPasswordAgainEd.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.joinPasswordAgainInvisibleIv.visibility = View.VISIBLE
            binding.joinPasswordAgainVisibleIv.visibility = View.GONE
        }


        // 닉네임
        nicknameEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                joinViewModel.setName(nicknameEdit.text.toString())

            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // 닉네임 x 버튼
        binding.joinNicknameIv1.setOnClickListener {
            binding.joinNicknameEd.text.clear()
            joinViewModel.setName("")
        }

        // 이용약관 동의 체크
        binding.checkboxTerm1On.setOnClickListener {
            binding.checkboxTerm1On.visibility = View.GONE
            binding.checkboxTerm1Off.visibility = View.VISIBLE
            checkbox1Bool = true
            val serviceAgreement = agreementList.find {it.name == "SERVICE"}
            serviceAgreement?.isAgreed = true
            validJoin()
        }
        binding.checkboxTerm1Off.setOnClickListener {
            binding.checkboxTerm1Off.visibility = View.GONE
            binding.checkboxTerm1On.visibility = View.VISIBLE
            checkbox1Bool = false
            val serviceAgreement = agreementList.find {it.name == "SERVICE"}
            serviceAgreement?.isAgreed = false
            validJoin()
        }

        binding.checkboxTerm2On.setOnClickListener {
            binding.checkboxTerm2On.visibility = View.GONE
            binding.checkboxTerm2Off.visibility = View.VISIBLE
            checkbox2Bool = true
            val personalAgreement = agreementList.find {it.name == "PERSONAL"}
            personalAgreement?.isAgreed = true
            validJoin()
        }
        binding.checkboxTerm2Off.setOnClickListener {
            binding.checkboxTerm2Off.visibility = View.GONE
            binding.checkboxTerm2On.visibility = View.VISIBLE
            checkbox2Bool = false
            val personalAgreement = agreementList.find {it.name == "PERSONAL"}
            personalAgreement?.isAgreed = false
            validJoin()
        }

        binding.checkboxTerm3On.setOnClickListener {
            binding.checkboxTerm3On.visibility = View.GONE
            binding.checkboxTerm3Off.visibility = View.VISIBLE
            val marketingAgreement = agreementList.find {it.name == "MARKETING"}
            marketingAgreement?.isAgreed = true
            validJoin()

        }
        binding.checkboxTerm3Off.setOnClickListener {
            binding.checkboxTerm3Off.visibility = View.GONE
            binding.checkboxTerm3On.visibility = View.VISIBLE
            val marketingAgreement = agreementList.find {it.name == "MARKETING"}
            marketingAgreement?.isAgreed = false
            validJoin()
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


    }

//    // 비밀번호 유효성 검사
//    fun validatePassword(password: String): Boolean {
//        // 비밀번호가 영문자를 포함하는지 확인
//        val containsEnglishChars = password.matches(Regex(".*[a-zA-Z].*"))
//        if (containsEnglishChars) {
//            binding.conditionEng.setTextColor(
//                ContextCompat.getColor(
//                    this@JoinActivity,
//                    R.color.beering_black
//                )
//            )
//            binding.check1.setImageResource(R.drawable.ic_check_dark)
//        } else {
//            binding.conditionEng.setTextColor(
//                ContextCompat.getColor(
//                    this@JoinActivity,
//                    R.color.gray01
//                )
//            )
//            binding.check1.setImageResource(R.drawable.ic_check_light)
//        }
//
//        // 비밀번호가 특수문자를 포함하는지 확인
//        val containsSpecialChars = password.matches(Regex(".*[!@#\\\$%].*"))
//        if (containsSpecialChars) {
//            binding.conditionCharacter.setTextColor(
//                ContextCompat.getColor(
//                    this@JoinActivity,
//                    R.color.beering_black
//                )
//            )
//            binding.check2.setImageResource(R.drawable.ic_check_dark)
//        } else {
//            binding.conditionCharacter.setTextColor(
//                ContextCompat.getColor(
//                    this@JoinActivity,
//                    R.color.gray01
//                )
//            )
//            binding.check2.setImageResource(R.drawable.ic_check_light)
//        }
//
//        // 비밀번호가 숫자를 포함하는지 확인
//        val containsNumbers = password.matches(Regex(".*[0-9].*"))
//        if (containsNumbers) {
//            binding.conditionNum.setTextColor(
//                ContextCompat.getColor(
//                    this@JoinActivity,
//                    R.color.beering_black
//                )
//            )
//            binding.check3.setImageResource(R.drawable.ic_check_dark)
//        } else {
//            binding.conditionNum.setTextColor(
//                ContextCompat.getColor(
//                    this@JoinActivity,
//                    R.color.gray01
//                )
//            )
//            binding.check3.setImageResource(R.drawable.ic_check_light)
//        }
//
//        // 비밀번호의 길이가 8자에서 20자 사이인지 확인
//        val isLengthValid = password.length in 8..20
//        if (isLengthValid) {
//            binding.conditionLength.setTextColor(
//                ContextCompat.getColor(
//                    this@JoinActivity,
//                    R.color.beering_black
//                )
//            )
//            binding.check4.setImageResource(R.drawable.ic_check_dark)
//        } else {
//            binding.conditionLength.setTextColor(
//                ContextCompat.getColor(
//                    this@JoinActivity,
//                    R.color.gray01
//                )
//            )
//            binding.check4.setImageResource(R.drawable.ic_check_light)
//        }
//
//        return containsEnglishChars && containsSpecialChars && containsNumbers && isLengthValid
//    }

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

    fun validJoin() {
        // 회원가입 버튼 활성화 그리고 api 연결
        if(idBool && passwordBool && nicknameBool && checkbox1Bool && checkbox2Bool) {
            binding.joinBtnLight.visibility = View.GONE
            binding.joinBtnDark.visibility = View.VISIBLE

            binding.joinBtnDark.setOnClickListener {
                val member = Member(joinViewModel.userId.value!!, joinViewModel.password.value!!, joinViewModel.name.value!!, agreementList)

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


}