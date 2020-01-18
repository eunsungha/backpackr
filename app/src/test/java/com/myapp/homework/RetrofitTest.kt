package com.myapp.homework

import com.myapp.data.RvItem
import com.myapp.network.RetrofitClientInstance
import com.myapp.network.model.RetroProducts
import com.myapp.service.GetDataService
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RetrofitTest {

    lateinit var service:GetDataService

    @Before
    fun init(){
        service = RetrofitClientInstance.getRetrofitInstance()
            .create(GetDataService::class.java)
    }

    @Test
    fun status_ok() {

        service.getProducts(1).enqueue(object: Callback<RetroProducts> {
            override fun onFailure(call: Call<RetroProducts>, t: Throwable) {

            }

            override fun onResponse(call: Call<RetroProducts>, response: Response<RetroProducts>) {
                val statusCode = response.body()?.statusCode
                assertEquals(statusCode, 200)
            }
        })
    }
}
