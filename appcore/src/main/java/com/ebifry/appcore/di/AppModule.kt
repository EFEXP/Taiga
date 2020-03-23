package com.ebifry.appcore.di


import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ebifry.appcore.domain.AppDatabase
import com.ebifry.appcore.domain.repository.AmazonRepository
import com.ebifry.appcore.domain.repository.DataRepository
import com.ebifry.appcore.repository.AmazonRepositoryImpl
import com.ebifry.appcore.repository.AmazonService
import com.ebifry.appcore.repository.DataRepositoryImpl
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
class AppModule(private val application:Application) {
    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase{
        return Room.databaseBuilder(application,AppDatabase::class.java,"empty").build()
    }
    @Provides
    @Singleton
    fun provideDataRepository(dataRepository: DataRepositoryImpl): DataRepository{
        return dataRepository
    }
    @Provides
    @Singleton
    fun provideAmazonRepository(amazon:AmazonRepositoryImpl): AmazonRepository {
        return amazon
    }
    @Provides
    @Singleton
    fun provideAmazonService(retrofit: Retrofit): AmazonService {
        return retrofit.create(AmazonService::class.java)
    }
    @Provides
    @Singleton
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
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://mws.amazonservices.jp")
            .addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideApplicationContext(): Context? {
        return application.applicationContext
    }



}