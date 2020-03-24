package com.ebifry.appbase

import android.app.Application
import com.ebifry.appbase.di.CoreComponent
import com.ebifry.appbase.di.CoreComponentProvider
import com.ebifry.appbase.di.CoreModule
import com.ebifry.appbase.di.DaggerCoreComponent
import com.google.firebase.analytics.FirebaseAnalytics

class Taiga: Application() , CoreComponentProvider {

    private val coreComponent by lazy {
        DaggerCoreComponent.builder().plus(CoreModule(this)).build()
    }
   //var applicationComponent: ApplicationComponent = DaggerApplicationComponent.builder().databaseModule(
     //  DatabaseModule(this)
 //  ).appModule(AppModule()).build()

    override fun onCreate() {
        FirebaseAnalytics.getInstance(this)
        super.onCreate()
    }

    override fun provideCoreComponent(): CoreComponent {
        return coreComponent
    }

}