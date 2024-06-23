package jp.co.yumemi.android.code_check

import android.app.Application
import jp.co.yumemi.android.code_check.data.koin.loadModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            loadModules()
        }
    }
}