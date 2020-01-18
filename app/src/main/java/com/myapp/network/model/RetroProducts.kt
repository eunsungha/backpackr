package com.myapp.network.model

data class RetroProducts(
    val statusCode: Int,
    val body: List<Product>
)

data class Product(
    val thumbnail_520:String,
    val id:Int,
    val seller:String,
    val title:String
)


