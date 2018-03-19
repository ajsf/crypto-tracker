package com.doublea

import com.google.gson.Gson
import okhttp3.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

class M2XPoster {

    var apiKey: String

    init {
        val props = Properties()
        props.load(FileInputStream(File("gradle.properties")))
        apiKey = props.getProperty("m2xApiKey")
        println(apiKey)
    }

    private val client = OkHttpClient()
    private val updateM2XPriceBuilder = Request.Builder()
            .url("https://api-m2x.att.com/v2/devices/d6029dabbecc041e8e68fa8c42e9a817/streams/eth-price/value")
            .addHeader("X-M2X-KEY", apiKey)
            .addHeader("Content-Type", "application/json")
    private val JSON = MediaType.parse("application/json; charset=utf-8")

    fun postToM2X(price: Price) {
        val priceJson = Gson().toJson(price)
        val body = RequestBody.create(JSON, priceJson)
        val request = updateM2XPriceBuilder
                .put(body)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                println("Request Failure")
            }

            override fun onResponse(call: Call?, response: Response?) {
                println("Request success")
            }
        })
    }
}