package com.myapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientInstance{
    companion object {
        private lateinit var retrofit: Retrofit
        private lateinit var httpClient: OkHttpClient
        var interceptor = HttpLoggingInterceptor()
        private val BASE_URL: String =
            "https://2jt4kq01ij.execute-api.ap-northeast-2.amazonaws.com/prod/"

        fun getRetrofitInstance(): Retrofit {
            httpClient = OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
            return retrofit
        }
    }
}