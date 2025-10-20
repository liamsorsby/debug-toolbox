package co.sorsby.tools.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserSettingsRepository(private val context: Context) {

    private companion object {
        val CRASHLYTICS_CONSENT = booleanPreferencesKey("crashlytics_consent")
        val USAGE_ANALYTICS_CONSENT = booleanPreferencesKey("usage_analytics_consent")
        val HAS_SEEN_CONSENT_SCREEN = booleanPreferencesKey("has_seen_consent_screen")
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
