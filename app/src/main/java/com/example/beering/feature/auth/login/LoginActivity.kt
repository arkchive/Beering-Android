package com.example.beering.feature.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.beering.R
import com.example.beering.feature.MainActivity
import com.example.beering.util.getRetrofit_sync
import com.example.beering.databinding.ActivityLoginBinding
import com.example.beering.feature.auth.join.JoinActivity
import com.example.beering.util.changeLogin
import com.example.beering.util.setMemberId
import com.example.beering.util.setToken
import retrofit2.Call
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignupCl.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

        binding.loginBackIv.setOnClickListener {
            finish()
        }

        binding.loginPasswordInvisibleIv.setOnClickListener {
            binding.loginPasswordEd.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.loginPasswordInvisibleIv.visibility = View.INVISIBLE
            binding.loginPasswordVisibleIv.visibility = View.VISIBLE
        }

        binding.loginPasswordVisibleIv.setOnClickListener {
            binding.loginPasswordEd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.loginPasswordInvisibleIv.visibility = View.VISIBLE
            binding.loginPasswordVisibleIv.visibility = View.INVISIBLE
        }



        //객체 생성
        val idEdit: EditText = binding.loginIdEd
        val passwordEdit: EditText = binding.loginPasswordEd
        val loginBtn: ConstraintLayout = binding.loginLoginButtonCl

        //메시지 담을 변수
        var id: String = ""
        var password: String = ""


        //버튼 비활성화
        loginBtn.isEnabled = false

        //EditText 값 있을때만 버튼 활성화
        idEdit.addTextChangedListener(object: TextWatcher {
            // 입력 하기 전에 작동
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            //값 변경 시 실행되는 함수
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                //id 입력값 담기
                id = idEdit.text.toString()

                if(id.isNotEmpty() && password.isNotEmpty() ){
                    loginBtn.setBackgroundColor(ContextCompat.getColor(this@LoginActivity,
                        R.color.black
                    ))
                    loginBtn.isEnabled = true
                } else {
                    loginBtn.setBackgroundColor(ContextCompat.getColor(this@LoginActivity,
                        R.color.gray03
                    ))
                    loginBtn.isEnabled = false
                }
            }

            // 입력이 끝날 때 작동
            override fun afterTextChanged(p0: Editable?) {}
        })



        passwordEdit.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            //값 변경 시 실행되는 함수
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                //입력값 담기
                password = passwordEdit.text.toString()

                //값 유무에 따른 활성화 여부
                if(id.isNotEmpty() && password.isNotEmpty() ){
                    loginBtn.setBackgroundColor(ContextCompat.getColor(this@LoginActivity,
                        R.color.black
                    ))
                    loginBtn.isEnabled = true
                } else {
                    loginBtn.setBackgroundColor(ContextCompat.getColor(this@LoginActivity,
                        R.color.gray03
                    ))
                    loginBtn.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })



        //버튼 이벤트
        loginBtn.setOnClickListener {
            singIn()
        }

        binding.loginIdDeleteIv.setOnClickListener {
            binding.loginIdEd.text.clear()
            id = ""
        }

        binding.loginPasswordDeleteIv.setOnClickListener {
            binding.loginPasswordEd.text.clear()
            password = ""
        }

        binding.loginPasswordEd.setOnKeyListener { view, i, keyEvent ->
            if ((keyEvent.action == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                if(loginBtn.isEnabled == true){
                    singIn()
                }
                true

            } else false
        }

    }

    fun singIn(){
        //api 연결
        val signInService = getRetrofit_sync().create(LoginApiService::class.java)
        val user = LoginRequest(binding.loginIdEd.text.toString(), binding.loginPasswordEd.text.toString())
        signInService.signIn(user).enqueue(object : retrofit2.Callback<LoginResponse>{
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                val resp = response.body()
                if(resp!!.isSuccess){
                    val userToken = resp!!.result.jwtInfo
                    setToken(this@LoginActivity, userToken)


                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                    changeLogin(this@LoginActivity, true)
                    setMemberId(this@LoginActivity, resp.result.memberId)

                } else {
                    binding.loginErrorTv.text = resp!!.responseMessage
                    binding.loginErrorTv.visibility = View.VISIBLE
                    binding.loginIdV.setBackgroundColor(ContextCompat.getColor(this@LoginActivity,
                        R.color.beering_red
                    ))
                    binding.loginPasswordV.setBackgroundColor(ContextCompat.getColor(this@LoginActivity,
                        R.color.beering_red
                    ))

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                binding.loginErrorTv.text = "서버에 요청을 실패하였습니다."
                binding.loginErrorTv.visibility = View.VISIBLE
                binding.loginIdV.setBackgroundColor(ContextCompat.getColor(this@LoginActivity,
                    R.color.beering_red
                ))
                binding.loginPasswordV.setBackgroundColor(ContextCompat.getColor(this@LoginActivity,
                    R.color.beering_red
                ))
            }

        })


    }


}

