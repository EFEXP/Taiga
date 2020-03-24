package com.ebifry.barcode.di

import com.ebifry.barcode.domain.usecase.ScanApp
import com.ebifry.barcode.domain.usecaseimpl.ScanAppImpl
import dagger.Module
import dagger.Provides

@Module
class MainViewModule {
    @Provides
    fun provideScanApp(scanAppImpl: ScanAppImpl): ScanApp { return scanAppImpl }
}