package com.imranmelikov.qrcodescankotlin.analyzer

import android.util.Log
import android.widget.Toast
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class MLKitBarcodeAnalyzer(private val listener: ScanningResultListener) : ImageAnalysis.Analyzer {

    private var isScanning: Boolean = false

    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null && !isScanning) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            // Pass image to an ML Kit Vision API
            // ...
            val scanner = BarcodeScanning.getClient()

            isScanning = true
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    // Task completed successfully
                    // ...

                    barcodes.firstOrNull().let { barcode ->
                        val rawValue = barcode?.rawValue
                        rawValue?.let {
                            if (isWebsiteUrl(it)) {
                                // QR kodu bir website URL'si olarak tanımlandı.
                                println("QR Code is a website URL")
                                listener.onScannedWebsite(it)
                            } else {
                                // QR kodu bir website URL'si değil.
                                Log.d("QR Code URL", it)
                                listener.onScanned(it)
                            }
//                            Log.d("Barcode", it)
//                            listener.onScanned(it)
                        }
                    }

                    isScanning = false
                    imageProxy.close()
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    // ...
                    Log.e("QR Code", "QR Code scanning failed: ${it.message}")
                    isScanning = false
                    imageProxy.close()
                }
        }
    }
    private fun isWebsiteUrl(rawValue: String): Boolean {
        // Basit bir URL doğrulama mantığı kullanabilirsiniz.
        // URL, genellikle "http://" veya "https://" ile başlar.
        return rawValue.startsWith("http://") || rawValue.startsWith("https://")
    }
}