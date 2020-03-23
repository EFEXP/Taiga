package com.ebifry.appcore.di

import com.ebifry.appcore.domain.entity.AuthParams
import com.ebifry.appcore.domain.usecase.ScanApp
import com.ebifry.appcore.domain.usecaseimpl.ScanAppImpl
import dagger.Module
import dagger.Provides

@Module
class MainViewModule {
    @Provides
    fun provideScanApp(scanAppImpl: ScanAppImpl): ScanApp { return scanAppImpl }
}