package com.ebifry.appcore.di

import com.ebifry.appcore.domain.repository.AmazonRepository
import com.ebifry.appcore.repository.AmazonService
import com.ebifry.appcore.ui.viewmodel.MainViewModel
import dagger.Component
import dagger.Subcomponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Subcomponent(modules = [MainViewModule::class])
interface  MainViewComponent  {
    fun inject(viewModel: MainViewModel)
}