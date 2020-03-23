package com.ebifry.appcore

import android.app.Application
import androidx.room.Room
import com.ebifry.appcore.di.AppModule
import com.ebifry.appcore.di.ApplicationComponent
import com.ebifry.appcore.di.DaggerApplicationComponent
import com.ebifry.appcore.domain.AppDatabase
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class Taiga: Application() {
   var applicationComponent: ApplicationComponent = DaggerApplicationComponent.builder().appModule(AppModule(this)).build()

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            CoroutineScope(Dispatchers.IO).launch {
                Room.databaseBuilder(applicationContext, AppDatabase::class.java,"empty").build().clearAllTables()
            }
        }
        FirebaseAnalytics.getInstance(this)
        super.onCreate()
    }

}