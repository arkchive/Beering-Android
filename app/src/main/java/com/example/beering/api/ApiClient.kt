package com.example.beering.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

const val BASE_URL = "https://api.beering.shop"

// 토큰 요청 (비동기 방식)
fun okHttpClient_header(header: String) : OkHttpClient {
    val builder = OkHttpClient.Builder()
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    builder.addInterceptor(HeaderInterceptor(header))
    return builder.addInterceptor(logging).build()
}
class HeaderInterceptor constructor(private val token: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = "Bearer $token"
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build()
        return chain.proceed(newRequest)
    }
}
fun getRetrofit_header(header: String): Retrofit{
    // 각 API 호출을 담당할 API 클라이언트 클래스
    // API 인터페이스를 생성하고 Retrofit 인스턴스 초기화

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client((okHttpClient_header(header)))
        .build()

    return retrofit
}


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
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client((okHttpClient()))
        .build()

    return retrofit
}

//동기 방식 (로그인에서 사용중)
fun getRetrofit_sync(): Retrofit{
    // 각 API 호출을 담당할 API 클라이언트 클래스
    // API 인터페이스를 생성하고 Retrofit 인스턴스 초기화

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit
}




// 동기 방식
class ApiClient {
    // 각 API 호출을 담당할 API 클라이언트 클래스
    // API 인터페이스를 생성하고 Retrofit 인스턴스 초기화

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // 필요한 API 인터페이스 속성 추가
    val joinApiService: JoinApiService = retrofit.create(JoinApiService::class.java)


}