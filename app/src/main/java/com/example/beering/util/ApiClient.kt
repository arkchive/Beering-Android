package com.example.beering.util

import com.example.beering.feature.auth.join.JoinApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

const val BASE_URL = "https://api.beering.shop/"

// 헤더 없는 okHttpClient
fun okHttpClient() : OkHttpClient {
    val builder = OkHttpClient.Builder()
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return builder.addInterceptor(logging).build()
}


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

fun getRetrofit(): Retrofit{
    // 각 API 호출을 담당할 API 클라이언트 클래스
    // API 인터페이스를 생성하고 Retrofit 인스턴스 초기화

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client((okHttpClient()))
        .build()

    return retrofit
}




