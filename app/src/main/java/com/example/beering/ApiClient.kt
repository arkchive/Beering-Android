package com.example.beering

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 비동기 방식
fun okHttpClient() : OkHttpClient {
    val builder = OkHttpClient.Builder()
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return builder.addInterceptor(logging).build()
}

fun getRetrofit_async(): Retrofit{
    // 각 API 호출을 담당할 API 클라이언트 클래스
    // API 인터페이스를 생성하고 Retrofit 인스턴스 초기화

    val retrofit = Retrofit.Builder()
        .baseUrl("https://beering-72501f868a10.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client((okHttpClient()))
        .build()

    return retrofit
}
//동기 방식
fun getRetrofit_sync(): Retrofit{
    // 각 API 호출을 담당할 API 클라이언트 클래스
    // API 인터페이스를 생성하고 Retrofit 인스턴스 초기화

    val retrofit = Retrofit.Builder()
        .baseUrl("https://beering-72501f868a10.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit
}




// 동기 방식
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