package me.vanjavk.recenzo.handler

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import me.vanjavk.recenzo.factory.createGetHttpUrlConnection
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection

private const val ARTICLE_PICTURE_WIDTH = 1000
private const val ARTICLE_PICTURE_HEIGHT = 750
private const val QUALITY = 100
private const val TAG = "ImagesHandler"

fun downloadImageAndStore(context: Context, url: String, fileName: String) : String? {
    // ext
    var extension = "";
    try {
        extension = url.substring(url.lastIndexOf(".")); // .jpeg
    }catch (e: Exception){}
    val file: File = getFile(context, fileName, extension)
    try {
        // moram otvoriti konekciju na url!!
        val con: HttpURLConnection = createGetHttpUrlConnection(url)
        println(url)
        // inpustream prema url da citam
        // outputstream prema file da pisem
        con.inputStream.use {`is` ->
            FileOutputStream(file).use {fos->
                // dohvati bitmap s url
                val bitmap = BitmapFactory.decodeStream(`is`)
                // resize bitmap na 1000x750
                val resizedBitmap : Bitmap = getResizedBitmap(
                    bitmap,
                    ARTICLE_PICTURE_WIDTH,
                    ARTICLE_PICTURE_HEIGHT
                )
                // moram bitmap pretvorit u bajtove
                val buffer: ByteArray = getBytesFromBitmap(resizedBitmap)
                fos.write(buffer)
                return file.absolutePath
            }

        }
    } catch (e: Exception) {
        Log.e(TAG, e.message, e)
    }

    return null
}

fun getBytesFromBitmap(bitmap: Bitmap): ByteArray {
    val bos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, bos)
    return bos.toByteArray()
}

fun getResizedBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
    val width = bitmap.width
    val height = bitmap.height
    val scaleWidth = newWidth.toFloat() / width
    val scaleHeight = newHeight.toFloat() / height
    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)
    return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)
}

fun getFile(context: Context, fileName: String, extension: String): File {
    val dir: File? = context.applicationContext.getExternalFilesDir(null)
    val file = File(dir, File.separator.toString() + fileName + extension)
    if (file.exists()) {
        file.delete()
    }
    return file
}
