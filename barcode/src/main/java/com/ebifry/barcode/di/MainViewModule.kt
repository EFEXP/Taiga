package com.ebifry.barcode.di

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.ebifry.appbase.di.ModuleScope
import com.ebifry.barcode.domain.usecase.ScanApp
import com.ebifry.barcode.domain.usecaseimpl.ScanAppImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainViewModule {
    @Provides
    @ModuleScope
    fun provideScanApp(scanAppImpl: ScanAppImpl): ScanApp { return scanAppImpl }
    @Provides
    @ModuleScope
    fun provideSharedPreference(app: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app)
    }
}