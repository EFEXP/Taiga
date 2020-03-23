package com.ebifry.appcore.repository

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.jsoup.Jsoup
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

class AmazonRepositoryImplTest {
    val c=OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getDetail() {



    }
}