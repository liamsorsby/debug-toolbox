package co.sorsby.tools

import android.app.Application
import co.sorsby.tools.data.local.UserSettingsRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ToolsApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    lateinit var userSettingsRepository: UserSettingsRepository
        private set

    override fun onCreate() {
        super.onCreate()
        userSettingsRepository = UserSettingsRepository(this)

        applicationScope.launch {
            val hasConsented = userSettingsRepository.analyticsConsent.first()
            FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = hasConsented
        }
    }
}
