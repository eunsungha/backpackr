package com.myapp.service

import com.myapp.network.model.RetroDetailProduct
import com.myapp.network.model.RetroProducts
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetDataService{

    @GET("products")
    fun getProducts(
        @Query("page") page: Int = 1
    ) : Call<RetroProducts>

    @GET("products/{id}")
    fun getDetailProduct(
        @Path("id") id:Int
    ) : Call<RetroDetailProduct>


}