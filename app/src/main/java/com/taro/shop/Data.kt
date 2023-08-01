package com.taro.shop

data class Data(

    val `data`: List<DataX>,
    val next: Int?
){

    data class DataX(
        val content: String,
        val id: Int,
        val images: List<String>,
        val link: String,
        val name: String
    )
}
