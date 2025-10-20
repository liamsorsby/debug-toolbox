package co.sorsby.tools

import android.app.Application
import co.sorsby.tools.data.local.UserSettingsRepository
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ToolsApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    val userSettingsRepository: UserSettingsRepository by lazy {
        UserSettingsRepository(this)
    }

    override fun onCreate() {
        super.onCreate()

        applicationScope.launch {
            val crashlyticsConsent = userSettingsRepository.crashlyticsConsent.first()
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(crashlyticsConsent)
        }

        applicationScope.launch {
            val analyticsConsent = userSettingsRepository.usageAnalyticsConsent.first()
            FirebaseAnalytics.getInstance(this@ToolsApplication).setAnalyticsCollectionEnabled(analyticsConsent)
        }
    }
}
