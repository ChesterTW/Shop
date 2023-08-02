package com.taro.shop

data class ParkingLot(
    val parkingLots: List<ParkingLotX>
) {
    data class ParkingLotX(
        val address: String,
        val areaId: String,
        val areaName: String,
        val introduction: String,
        val parkId: String,
        val parkName: String,
        val payGuide: String,
        val surplusSpace: String,
        val totalSpace: Int,
        val wgsX: Double,
        val wgsY: Double
    )
}

