package com.ebifry.appcore.di

import com.ebifry.appcore.domain.repository.AmazonRepository
import com.ebifry.appcore.domain.repository.DataRepository
import com.ebifry.appcore.repository.AmazonService
import com.ebifry.appcore.repository.RequestBuilder
import com.ebifry.appcore.ui.viewmodel.MainViewModel
import dagger.Component
import dagger.Subcomponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface  ApplicationComponent  {
    fun createAmazonService(): AmazonService
    fun createAmazonRepo(): AmazonRepository
    fun createRetrofit(): Retrofit
    fun createDataRepo(): DataRepository
    fun plus(mainViewModule: MainViewModule):MainViewComponent
}