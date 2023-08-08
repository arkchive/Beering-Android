package com.example.beering

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    // 각 API 호출을 담당할 API 클라이언트 클래스
    // API 인터페이스를 생성하고 Retrofit 인스턴스 초기화

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://beering-72501f868a10.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // 필요한 API 인터페이스 속성 추가
    val joinApiService: JoinApiService = retrofit.create(JoinApiService::class.java)


}