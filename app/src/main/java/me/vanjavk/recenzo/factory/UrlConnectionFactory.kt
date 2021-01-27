package me.vanjavk.recenzo.factory

import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

const val TIMEOUT = 10000
const val REQUEST_METHOD = "GET"
const val USER_AGENT = "User-Agent"
const val MOZILLA = "Mozilla/5.0"

fun createGetHttpUrlConnection(path: String) : HttpURLConnection{
    val url = URL(path)
    return (url.openConnection() as HttpsURLConnection).apply {
        connectTimeout = TIMEOUT
        readTimeout = TIMEOUT
        requestMethod = REQUEST_METHOD
        addRequestProperty(USER_AGENT, MOZILLA)
    }
}