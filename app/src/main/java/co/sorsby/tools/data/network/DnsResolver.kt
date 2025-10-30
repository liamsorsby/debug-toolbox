package co.sorsby.tools.data.network

import co.sorsby.tools.data.local.UserSettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.xbill.DNS.Cache
import org.xbill.DNS.ExtendedResolver
import org.xbill.DNS.Lookup
import org.xbill.DNS.Record
import org.xbill.DNS.SimpleResolver
import org.xbill.DNS.TextParseException
import java.time.Duration

class DnsResolver(
    private val userSettingsRepository: UserSettingsRepository,
) {
    suspend fun lookup(
        hostname: String,
        type: Int,
    ): Result<Array<Record>?> {
        return withContext(Dispatchers.IO) {
            try {
                val server1 = userSettingsRepository.dnsServer.first()
                val server2 = userSettingsRepository.dnsServer2.first()
                val ndots = userSettingsRepository.dnsNdots.first()
                val timeout = userSettingsRepository.dnsTimeout.first()

                val servers =
                    listOf(server1, server2)
                        .filter { it.isNotBlank() }
                        .map {
                            SimpleResolver(it).apply {
                                setTimeout(Duration.ofSeconds(timeout.toLong()))
                            }
                        }.toTypedArray()

                if (servers.isEmpty()) {
                    return@withContext Result.failure(IllegalStateException("No DNS servers configured."))
                }

                val resolver = ExtendedResolver(servers)

                val lookup = Lookup(hostname, type)
                lookup.setResolver(resolver)
                lookup.setCache(Cache())

                lookup.setNdots(ndots)

                val records = lookup.run()

                if (lookup.result == Lookup.SUCCESSFUL) {
                    Result.success(records)
                } else {
                    Result.failure(Exception("Lookup failed: ${lookup.errorString}"))
                }
            } catch (e: TextParseException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
