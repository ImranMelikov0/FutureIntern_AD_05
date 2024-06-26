package com.imranmelikov.qrcodescankotlin.analyzer

interface ScanningResultListener {
    fun onScanned(result: String)
    fun onScannedWebsite(result: String)
}