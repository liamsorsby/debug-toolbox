package co.sorsby.tools

import android.app.Application
import co.sorsby.tools.data.local.UserSettingsRepository
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ToolsApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    val userSettingsRepository: UserSettingsRepository by lazy {
        UserSettingsRepository(this)
    }

    override fun onCreate() {
        super.onCreate()
        observeConsentChanges()
    }

    private fun observeConsentChanges() {
        userSettingsRepository.crashlyticsConsent
            .onEach { isEnabled ->
                FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(isEnabled)
            }.launchIn(applicationScope)

        userSettingsRepository.usageAnalyticsConsent
            .onEach { isEnabled ->
                FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(isEnabled)
            }.launchIn(applicationScope)
    }
}
