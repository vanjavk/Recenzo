package me.vanjavk.recenzo

import android.Manifest
import android.R.attr.label
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.fragment_scan.*
import me.vanjavk.recenzo.framework.startActivity
import me.vanjavk.recenzo.model.Product
import me.vanjavk.recenzo.products.ITEM_BARCODE
import me.vanjavk.recenzo.products.ProductPagerActivity
import java.io.IOException


class ScanFragment : Fragment() {
    private lateinit var surfaceView: SurfaceView
    private lateinit var barcodeDetector: BarcodeDetector
    private lateinit var cameraSource: CameraSource
    private var REQUEST_CAMERA_PERMISSION = 201

    //This class provides methods to play DTMF tones
    private lateinit var toneGen1: ToneGenerator
    private lateinit var barcodeText: TextView
    private lateinit var barcodeData: String


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toneGen1 = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        surfaceView = surface_view
        barcodeText = barcode_text

        setupListeners()
        initialiseDetectorsAndSources()
    }

    private fun setupListeners() {
        btnCamera.setOnClickListener {
//            startActivity(Intent.makeRestartActivityTask(activity?.intent?.component))
            checkCameraPermissionsAndInitialize()
        }
        btnViewProduct.setOnClickListener {
            checkIfProductInDatabase()
        }
        btnCopy.setOnClickListener {
            copyToClipboard()
        }
    }

    private fun copyToClipboard() {
        val clipboard = getSystemService(
            requireContext(),
            ClipboardManager::class.java
        )
        val clip = ClipData.newPlainText("Copied stuff", barcodeText.text)
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip)
        }
    }


    private fun checkIfProductInDatabase(){
        if (barcode_text.text != null) {
            val cursor = requireContext().contentResolver.query(
                RECENZO_PROVIDER_CONTENT_URI,
                null, "barcode='${barcode_text.text}'", null, null
            )
            if (cursor != null && !(!cursor.moveToFirst() || cursor.getCount() == 0)) {
                val product =
                    Product(
                        cursor.getLong(cursor.getColumnIndex(Product::_id.name)),
                        cursor.getString(cursor.getColumnIndex(Product::barcode.name)),
                        cursor.getString(cursor.getColumnIndex(Product::title.name)),
                        cursor.getString(cursor.getColumnIndex(Product::description.name)),
                        cursor.getString(cursor.getColumnIndex(Product::picturePath.name)),
                    )
                requireContext().startActivity<ProductPagerActivity>(ITEM_BARCODE, product.barcode)
            } else {
                Toast.makeText(requireContext(), "Product not in database", Toast.LENGTH_SHORT)
                    .show();
            }
        }
    }
    private fun checkCameraPermissionsAndInitialize(){
        try {
            if (context?.let {
                    checkSelfPermission(
                        it,
                        Manifest.permission.CAMERA
                    )
                } == PackageManager.PERMISSION_GRANTED
            ) {
                cameraSource.start(surfaceView.holder)
            } else {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA_PERMISSION
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun initialiseDetectorsAndSources() {
        barcodeDetector = BarcodeDetector.Builder(context)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()
        cameraSource = CameraSource.Builder(context, barcodeDetector)
            .setRequestedPreviewSize(1280, 720)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                checkCameraPermissionsAndInitialize()
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })
        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            override fun receiveDetections(detections: Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() != 0) {
                    barcodeText.post {
                        if (barcodes.valueAt(0).email != null) {
                            barcodeText.removeCallbacks(null)
                            barcodeData = barcodes.valueAt(0).email.address
                            barcodeText.text = barcodeData
                            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150)
                        } else {
                            barcodeData = barcodes.valueAt(0).displayValue
                            barcodeText.text = barcodeData
                            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150)
                        }
                    }
                }
            }
        })
    }

}



