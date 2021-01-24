package me.vanjavk.recenzo.api

import android.content.ContentValues
import android.content.Context
import android.util.Log

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecenzoFetcher(private val context: Context) {

    private var recenzoApi: RecenzoApi
    init {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(API_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        recenzoApi = retrofit.create(NasaApi::class.java)
//        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }
//
//    fun fetchItems() {
//        val request = recenzoApi.fetchItems()
//        // izvrsiti u backgroundu
//        request.enqueue(object: Callback<List<NasaItem>>{
//            override fun onResponse(
//                call: Call<List<NasaItem>>,
//                response: Response<List<NasaItem>>
//            ) {
//                if (response.body() != null) {
//                    populateItems(response.body()!!)
//                }
//            }
//
//            override fun onFailure(call: Call<List<NasaItem>>, t: Throwable) {
//                Log.d(javaClass.name, t.message, t)
//            }
//
//        })
//    }
//
//    private fun populateItems(nasaItems: List<NasaItem>) {
//        // vratio sam se u foreground
//        // moram opet u background
//        // coroutines!!!
//        GlobalScope.launch {
//            nasaItems.forEach {
//                val picturePath = downloadImageAndStore(context, it.url, it.title.replace(" ", "_") )
//                val values = ContentValues().apply {
//                    put(Item::title.name, it.title)
//                    put(Item::explanation.name, it.explanation)
//                    put(Item::picturePath.name, picturePath ?: "")
//                    put(Item::date.name, it.date)
//                    put(Item::read.name, false)
//                }
//                context.contentResolver.insert(NASA_PROVIDER_CONTENT_URI, values)
//            }
//            // obavijesti da si gotov
//            context.sendBroadcast<NasaReceiver>()
//        }
//    }
}