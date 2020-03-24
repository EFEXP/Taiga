package com.ebifry.barcode.di

import com.ebifry.barcode.repository.RequestBuilder
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AmazonAuthModule::class])
@Singleton
interface  AmazonAuthComponent {
    fun inject(requestBuilder: RequestBuilder)
}