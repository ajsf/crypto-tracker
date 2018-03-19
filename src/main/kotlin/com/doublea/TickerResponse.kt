package com.doublea

data class TickerResponse(
        val type: String,
        val sequence: Long,
        val product_id: String,
        val price: String
)