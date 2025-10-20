package co.sorsby.tools.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

class UserSettingsRepository(private val context: Context) {

    private object Keys {
        val ANALYTICS_CONSENT = booleanPreferencesKey("analytics_consent")
        val HAS_SEEN_CONSENT_SCREEN = booleanPreferencesKey("has_seen_consent_screen")
    }

    val analyticsConsent: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[Keys.ANALYTICS_CONSENT] ?: false
        }

    val hasSeenConsentScreen: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[Keys.HAS_SEEN_CONSENT_SCREEN] ?: false
        }

    suspend fun setAnalyticsConsent(hasConsented: Boolean) {
        context.dataStore.edit { settings ->
            settings[Keys.ANALYTICS_CONSENT] = hasConsented
        }
    }

    suspend fun setHasSeenConsentScreen(hasSeen: Boolean) {
        context.dataStore.edit { settings ->
            settings[Keys.HAS_SEEN_CONSENT_SCREEN] = hasSeen
        }
    }
}
