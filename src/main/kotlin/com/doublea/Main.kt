package com.doublea

import okhttp3.*

val websocketRequest = Request.Builder()
        .url("wss://ws-feed.gdax.com")
        ?.build()

val client = OkHttpClient()

val listener = GdaxWebsocketListener()

fun main(args: Array<String>) {
    println(listener.subMessage)
    client.newWebSocket(websocketRequest, listener)
}