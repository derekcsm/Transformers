package xyz.derekcsm.transformers.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import xyz.derekcsm.transformers.persistence.AppDatabase
import xyz.derekcsm.transformers.persistence.TransformersDao
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "Transformers.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideTransformersDao(appDatabase: AppDatabase): TransformersDao {
        return appDatabase.transformersDao()
    }
}
