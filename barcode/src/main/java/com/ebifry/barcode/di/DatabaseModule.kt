package com.ebifry.barcode.di


import androidx.lifecycle.LiveData
import com.ebifry.appbase.AppDatabase
import com.ebifry.appbase.dao.ScannedItemDAO
import com.ebifry.appbase.di.ModuleScope
import com.ebifry.barcode.domain.repository.DataRepository
import com.ebifry.barcode.repository.DataRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    @ModuleScope
    fun provideDataRepository(db:AppDatabase): DataRepository {
        return DataRepositoryImpl(db)
    }
    @Provides
    @ModuleScope
    fun provideHistoryLiveData(dataRepository:DataRepository ): LiveData<List<ScannedItemDAO.RetrievedItem>> {
            return dataRepository.getScanHistory()
    }

}