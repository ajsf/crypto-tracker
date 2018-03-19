package com.doublea

import com.google.gson.Gson
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject

class GdaxWebsocketListener : WebSocketListener() {

    private val m2XPoster = M2XPoster()

    val subMessage = JSONObject()
            .put("type", "subscribe")
            .put("channels", JSONArray()
                    .put(JSONObject()
                            .put("name", "ticker")
                            .put("product_ids", JSONArray()
                                    .put("ETH-USD")
                                    .put("BTC-USD")
                                    .put("LTC-USD")
                                    .put("BCH-USD"))
                    )
            ).toString()

    override fun onOpen(webSocket: WebSocket, response: Response) {
        webSocket.send(subMessage)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val tickerResponse = Gson().fromJson(text, TickerResponse::class.java)
        println(tickerResponse)
        if (tickerResponse.product_id == "ETH-USD") {
            val price = Price(tickerResponse.price)
            m2XPoster.postToM2X(price)
        }
    }
}