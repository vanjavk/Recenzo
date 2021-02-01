package me.vanjavk.recenzo.framework

import android.app.Activity
import android.content.*

import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.view.animation.AnimationUtils
import androidx.preference.PreferenceManager


import me.vanjavk.recenzo.RECENZO_PROVIDER_CONTENT_URI
import me.vanjavk.recenzo.products.ProductPagerAdapter
import me.vanjavk.recenzo.model.Product


fun View.applyAnimation(resourceId: Int) =
    startAnimation(AnimationUtils.loadAnimation(context, resourceId))

inline fun<reified T: Activity> Context.startActivity() = startActivity(Intent(this, T::class.java).addFlags(
    Intent.FLAG_ACTIVITY_NEW_TASK))

inline fun<reified T: Activity> Context.startActivity(key: String, value: Int) = startActivity(Intent(this, T::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).apply { putExtra(key, value) })
inline fun<reified T: Activity> Context.startActivity(key: String, value: String) = startActivity(Intent(this, T::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).apply { putExtra(key, value) })


inline fun<reified T: BroadcastReceiver> Context.sendBroadcast() = sendBroadcast(Intent(this, T::class.java))

inline fun ProductPagerAdapter.ViewHolder.updateRatingVisual(productRating: Int) {
    for (rating in 0..productRating)when(rating){
        0->{
            ivStar1.setColorFilter(Color.BLACK)
            ivStar2.setColorFilter(Color.BLACK)
            ivStar3.setColorFilter(Color.BLACK)
            ivStar4.setColorFilter(Color.BLACK)
            ivStar5.setColorFilter(Color.BLACK)
        }
        1->ivStar1.setColorFilter(Color.YELLOW)
        2->ivStar2.setColorFilter(Color.YELLOW)
        3->ivStar3.setColorFilter(Color.YELLOW)
        4->ivStar4.setColorFilter(Color.YELLOW)
        5->ivStar5.setColorFilter(Color.YELLOW)
    }
}



fun Context.setBooleanPreference(key: String, value: Boolean) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .edit()
        .putBoolean(key, value)
        .apply()

fun Context.getBooleanPreference(key: String) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .getBoolean(key, false)

fun updateProductRating(context: Context,product:Product){
    context.contentResolver.update(
        ContentUris.withAppendedId(RECENZO_PROVIDER_CONTENT_URI, product._id!!),
        ContentValues().apply {
            put(Product::rating.name, product.rating)
        },
        null,
        null
    )
}

fun Context.isOnline() : Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    if (network != null) {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        if (networkCapabilities != null) {
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }
    }
    return false
}

fun Context.fetchProducts() : MutableList<Product> {
    val products = mutableListOf<Product>()
    val cursor = contentResolver?.query(RECENZO_PROVIDER_CONTENT_URI,
        null, null, null, null)
    if (cursor != null) {
        while(cursor.moveToNext()) {
            products.add(
                Product(
                    cursor.getLong(cursor.getColumnIndex(Product::_id.name)),
                    cursor.getString(cursor.getColumnIndex(Product::barcode.name)),
                    cursor.getString(cursor.getColumnIndex(Product::title.name)),
                    cursor.getString(cursor.getColumnIndex(Product::description.name)),
                    cursor.getString(cursor.getColumnIndex(Product::picturePath.name)),
                    cursor.getInt(cursor.getColumnIndex(Product::rating.name)),
                )
            )
        }
    }

    return products
}
