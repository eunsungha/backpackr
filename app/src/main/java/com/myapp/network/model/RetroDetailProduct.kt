package com.myapp.network.model

data class RetroDetailProduct(
    val statusCode: Int,
    val body: List<DetailProduct>
)

data class DetailProduct(
    val id:Int,
    val thumbnail_720:String,
    val thumbnail_list_320:String,
    val title:String,
    val seller:String,
    val cost:String,
    val discount_cost:String,
    val discount_rate:String,
    val description:String
)


