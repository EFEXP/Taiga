package com.ebifry.barcode.di

import android.content.SharedPreferences
import com.ebifry.appbase.di.CoreComponent
import com.ebifry.appbase.di.ModuleScope
import com.ebifry.barcode.domain.repository.AmazonRepository
import com.ebifry.barcode.domain.repository.DataRepository
import com.ebifry.barcode.repository.AmazonService
import com.ebifry.barcode.ui.viewmodel.MainViewModel
import dagger.Component
import retrofit2.Retrofit

@Component(modules = [AppModule::class, DatabaseModule::class,MainViewModule::class],dependencies = [CoreComponent::class])
@ModuleScope
interface  ApplicationComponent  {
    fun createAmazonService(): AmazonService
    fun createAmazonRepo(): AmazonRepository
    fun createRetrofit(): Retrofit
    fun createDataRepo(): DataRepository
    fun plus(mainViewModel: MainViewModel)
}