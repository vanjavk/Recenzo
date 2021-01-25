package me.vanjavk.recenzo.api

import retrofit2.Call
import retrofit2.http.GET

const val API_URL = "https://privateip1337.lets.ee:8443/"
interface RecenzoApi {
    @GET("products")
    fun fetchItems() : Call<List<RecenzoProduct>>
}