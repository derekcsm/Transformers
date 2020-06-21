package xyz.derekcsm.transformers.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import xyz.derekcsm.transformers.network.ApiService
import xyz.derekcsm.transformers.persistence.TransformersDao
import xyz.derekcsm.transformers.repository.CreateEditTransformerRepository
import xyz.derekcsm.transformers.repository.TransformersRepository

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideTransformersRepository(
        apiService: ApiService,
        transformersDao: TransformersDao
    ): TransformersRepository {
        return TransformersRepository(apiService, transformersDao)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideCreateEditRepository(
        apiService: ApiService,
        transformersDao: TransformersDao
    ): CreateEditTransformerRepository {
        return CreateEditTransformerRepository(apiService, transformersDao)
    }
}