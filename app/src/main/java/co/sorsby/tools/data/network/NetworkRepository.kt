package co.sorsby.tools.data.network

import co.sorsby.tools.data.network.model.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.security.cert.X509Certificate
import java.text.SimpleDateFormat
import java.util.Locale

class NetworkRepository(
    private val client: OkHttpClient = OkHttpClient()
) {
    suspend fun executeRequest(url: String): NetworkResponse = withContext(Dispatchers.IO) {
        val request = Request.Builder().url(url).build()

        client.newCall(request).execute().use { response ->
            val headers = response.headers.toMultimap()
                .mapValues { it.value.joinToString("; ") }

            val certificateHosts = response.handshake?.peerCertificates?.mapNotNull { certificate ->
                (certificate as? X509Certificate)?.let { x509 ->
                    val sanList = x509.subjectAlternativeNames
                    sanList?.mapNotNull { entry ->
                        val type = entry[0] as? Int
                        val value = entry[1] as? String
                        // Type 2 = dNSName
                        if (type == 2) value else null
                    }
                }
            }?.flatten() ?: emptyList()

            val CertificatExpiryInfo = response.handshake?.peerCertificates?.mapNotNull { cert ->
                (cert as? X509Certificate)?.let { x509 ->
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val expiresOn = sdf.format(x509.notAfter)
                    val issuer = x509.issuerX500Principal.name
                    val subject = x509.subjectX500Principal.name
                    "Subject: $subject, Issuer: $issuer, Expires: $expiresOn"
                }
            }?: emptyList()

            NetworkResponse(
                status = "${response.code} ${response.message}",
                headers = headers,
                body = response.body?.string(),
                tlsVersion = response.handshake?.tlsVersion,
                cipherSuite = response.handshake?.cipherSuite,
                certificates = certificateHosts,
                certificateExpiryInfo = CertificatExpiryInfo
            )
        }
    }
}
