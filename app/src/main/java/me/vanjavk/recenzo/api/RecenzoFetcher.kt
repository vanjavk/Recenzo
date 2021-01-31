package me.vanjavk.recenzo.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import me.vanjavk.recenzo.RECENZO_PROVIDER_CONTENT_URI
import me.vanjavk.recenzo.framework.fetchProducts
import me.vanjavk.recenzo.framework.sendBroadcast
import me.vanjavk.recenzo.handler.downloadImageAndStore
import me.vanjavk.recenzo.model.Product
import okhttp3.MediaType

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception

class RecenzoFetcher(private val context: Context) {

    private var recenzoApi: RecenzoApi

    init {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(API_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        recenzoApi = retrofit.create(NasaApi::class.java)
//        val contentType = "application/json".toMediaType()
        val contentType = MediaType.get("application/json")
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
            .build()
        recenzoApi = retrofit.create(RecenzoApi::class.java)
    }

    fun fetchItems() {
        val request = recenzoApi.fetchItems()

        // izvrsiti u backgroundu
        request.enqueue(object : Callback<List<RecenzoProduct>> {

            override fun onResponse(
                call: Call<List<RecenzoProduct>>,
                response: Response<List<RecenzoProduct>>
            ) {
                if (response.body() != null) {
                    try {
                        if (context.fetchProducts().size == 0) {
                            populateItems(response.body()!!)
                        }else{
                            context.sendBroadcast<RecenzoReceiver>()
                        }
                    } catch (e: Exception) {
                        populateItems(response.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<List<RecenzoProduct>>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
                Toast.makeText(
                    context,
                    "Internet connection failure. Try restarting application.",
                    Toast.LENGTH_LONG
                ).show()
                context.sendBroadcast<RecenzoReceiver>()
            }
        })
    }

    private fun populateItems(recenzoItems: List<RecenzoProduct>) {
        // vratio sam se u foreground
        // moram opet u background
        // coroutines!!!
        GlobalScope.launch {
            recenzoItems.forEach {
                println(it)
                var picturePath: String? = null
                try {
                    picturePath =
                        downloadImageAndStore(context, it.picturePath, it.title.replace(" ", "_"))
                } finally {
                    val values = ContentValues().apply {
                        put(Product::barcode.name, it.barcode)
                        put(Product::title.name, it.title)
                        put(Product::description.name, it.description)
                        put(Product::picturePath.name, picturePath ?: "")
                    }
                    context.contentResolver.insert(RECENZO_PROVIDER_CONTENT_URI, values)
                }
            }
            // obavijesti da si gotov
            context.sendBroadcast<RecenzoReceiver>()
        }
    }
}