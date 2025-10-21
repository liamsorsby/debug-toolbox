package co.sorsby.tools.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserSettingsRepository(
    private val context: Context,
) {
    private companion object {
        val CRASHLYTICS_CONSENT = booleanPreferencesKey("crashlytics_consent")
        val USAGE_ANALYTICS_CONSENT = booleanPreferencesKey("usage_analytics_consent")
        val HAS_SEEN_CONSENT_SCREEN = booleanPreferencesKey("has_seen_consent_screen")

        val DNS_SERVER = stringPreferencesKey("dns_server")
        val DNS_SERVER_2 = stringPreferencesKey("dns_server_2")
        val DNS_SEARCH_PATHS = stringSetPreferencesKey("dns_search_paths")
        val DNS_NDOTS = intPreferencesKey("dns_ndots")
        val DNS_TIMEOUT = intPreferencesKey("dns_timeout")
    }

    val crashlyticsConsent: Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[CRASHLYTICS_CONSENT] ?: false
        }

    val usageAnalyticsConsent: Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[USAGE_ANALYTICS_CONSENT] ?: false
        }

    val hasSeenConsentScreen: Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[HAS_SEEN_CONSENT_SCREEN] ?: false
        }

    val dnsServer: Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[DNS_SERVER] ?: "8.8.8.8"
        }

    val dnsServer2: Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[DNS_SERVER_2] ?: "8.8.4.4"
        }

    val dnsNdots: Flow<Int> =
        context.dataStore.data.map { preferences ->
            preferences[DNS_NDOTS] ?: 1
        }

    val dnsTimeout: Flow<Int> =
        context.dataStore.data.map { preferences ->
            preferences[DNS_TIMEOUT] ?: 5
        }

    suspend fun setCrashlyticsConsent(consent: Boolean) {
        context.dataStore.edit { settings ->
            settings[CRASHLYTICS_CONSENT] = consent
        }
    }

    suspend fun setUsageAnalyticsConsent(consent: Boolean) {
        context.dataStore.edit { settings ->
            settings[USAGE_ANALYTICS_CONSENT] = consent
        }
    }

    suspend fun setHasSeenConsentScreen(hasSeen: Boolean) {
        context.dataStore.edit { settings ->
            settings[HAS_SEEN_CONSENT_SCREEN] = hasSeen
        }
    }

    suspend fun setDnsServer(server: String) {
        context.dataStore.edit { settings ->
            settings[DNS_SERVER] = server
        }
    }

    suspend fun setDnsServer2(server: String) {
        context.dataStore.edit { settings ->
            settings[DNS_SERVER_2] = server
        }
    }

    suspend fun setDnsSearchPaths(paths: Set<String>) {
        context.dataStore.edit { settings ->
            settings[DNS_SEARCH_PATHS] = paths
        }
    }

    suspend fun setDnsNdots(ndots: Int) {
        context.dataStore.edit { settings ->
            settings[DNS_NDOTS] = ndots
        }
    }

    suspend fun setDnsTimeout(timeout: Int) {
        context.dataStore.edit { settings ->
            settings[DNS_TIMEOUT] = timeout
        }
    }

    suspend fun toggleCrashlyticsConsent() {
        val current = context.dataStore.data.first()[CRASHLYTICS_CONSENT] ?: false
        setCrashlyticsConsent(!current)
    }

    suspend fun toggleUsageAnalyticsConsent() {
        val current = context.dataStore.data.first()[USAGE_ANALYTICS_CONSENT] ?: false
        setUsageAnalyticsConsent(!current)
    }

    suspend fun toggleHasSeenConsentScreen() {
        val current = context.dataStore.data.first()[HAS_SEEN_CONSENT_SCREEN] ?: false
        setHasSeenConsentScreen(!current)
    }
}
