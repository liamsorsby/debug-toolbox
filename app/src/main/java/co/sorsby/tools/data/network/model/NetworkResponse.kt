package co.sorsby.tools.data.network.model

import okhttp3.CipherSuite
import okhttp3.TlsVersion

data class NetworkResponse(
    val status: String,
    val headers: Map<String, String>,
    val body: String?,
    val tlsVersion: TlsVersion?,
    val cipherSuite: CipherSuite?,
    val certificates: List<String>?,
    val certificateExpiryInfo: List<String>,
)
