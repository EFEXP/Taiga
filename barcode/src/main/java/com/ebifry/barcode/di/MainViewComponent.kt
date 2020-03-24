package com.ebifry.barcode.di

import com.ebifry.barcode.ui.viewmodel.MainViewModel
import dagger.Subcomponent

@Subcomponent(modules = [MainViewModule::class])
interface  MainViewComponent  {
    fun inject(viewModel: MainViewModel)
}