package com.ebifry.appcore.di

import com.ebifry.appcore.domain.entity.AuthParams
import com.ebifry.appcore.domain.repository.AmazonRepository
import com.ebifry.appcore.repository.AmazonService
import com.ebifry.appcore.repository.RequestBuilder
import com.ebifry.appcore.ui.viewmodel.MainViewModel
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Component(modules = [AmazonAuthModule::class])
@Singleton
interface  AmazonAuthComponent {
    fun inject(requestBuilder: RequestBuilder)
}