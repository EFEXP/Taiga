package com.ebifry.appbase

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.ebifry.appbase.di.CoreComponent
import com.ebifry.appbase.di.CoreComponentProvider
import com.ebifry.appbase.di.CoreModule
import com.ebifry.appbase.di.DaggerCoreComponent
import com.google.firebase.analytics.FirebaseAnalytics

class Taiga: Application() , CoreComponentProvider {

    private val coreComponent by lazy {
        DaggerCoreComponent.builder().plus(CoreModule(this)).build()
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseAnalytics.getInstance(this)

    }

    override fun provideCoreComponent(): CoreComponent {
        return coreComponent
    }

}