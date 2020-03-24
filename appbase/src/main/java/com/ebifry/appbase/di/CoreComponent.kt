package com.ebifry.appbase.di

import android.app.Application
import androidx.room.Room
import com.ebifry.appbase.AppDatabase
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        CoreModule::class
    ]
)
interface CoreComponent {
    @Component.Builder
    interface Builder {
        fun build(): CoreComponent
        fun plus(coreModule: CoreModule): Builder
    }
    fun createAppDatabase(): AppDatabase
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ModuleScope

@Module
class CoreModule(
    private val application: Application
) {
    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "empty").build()
    }

}

interface CoreComponentProvider {
    fun provideCoreComponent(): CoreComponent
}