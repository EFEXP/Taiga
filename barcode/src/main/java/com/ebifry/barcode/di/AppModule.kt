package com.ebifry.barcode.di


import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.ebifry.appbase.di.ModuleScope
import com.ebifry.barcode.domain.repository.AmazonRepository
import com.ebifry.barcode.repository.AmazonRepositoryImpl
import com.ebifry.barcode.repository.AmazonService
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class AppModule {
    @Provides
    @ModuleScope
    fun provideAmazonRepository(amazon:AmazonRepositoryImpl): AmazonRepository {
        return amazon
    }
    @Provides
    @ModuleScope
    fun provideAmazonService(retrofit: Retrofit): AmazonService {
        return retrofit.create(AmazonService::class.java)
    }
    @Provides
    @ModuleScope
    fun provideOkHttpClient(): OkHttpClient {
        return  OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
    @Provides
    @ModuleScope
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://mws.amazonservices.jp")
            .addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
            .client(client)
            .build()
    }





}