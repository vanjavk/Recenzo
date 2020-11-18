package me.vanjavk.recenzo

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.serialization.json.Json
import me.vanjavk.recenzo.model.Product
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        setupListeners()
    }

    private fun setupListeners() {
        btnLoad.setOnClickListener { loadData() }

    }

    private fun loadData() {
        val jsonResponse = URL("https://privateip1337.lets.ee:8443/product").readText()
        //println(jsonResponse)
        val obj : Product = Json.decodeFromString(
            Product.serializer(),
            jsonResponse
        )
        //println(obj)

        tvDescription.text = obj.decription
        tvTitle.text = obj.id.toString()
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_EAN_13,
                Barcode.FORMAT_EAN_8)
            .build()
        val scanner = BarcodeScanning.getClient()
//        val result = scanner.process(image)
//            .addOnSuccessListener { barcodes ->
//                // Task completed successfully
//                // ...
//            }
//            .addOnFailureListener {
//                // Task failed with an exception
//                // ...
//            }
    }
}

private class YourImageAnalyzer : ImageAnalysis.Analyzer {

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            // Pass image to an ML Kit Vision API
            // ...
        }
    }
}