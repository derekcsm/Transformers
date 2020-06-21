package xyz.derekcsm.transformers.di

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.derekcsm.transformers.base.SharedPref
import xyz.derekcsm.transformers.network.ApiAuthenticator
import xyz.derekcsm.transformers.network.ApiService
import xyz.derekcsm.transformers.network.RequestInterceptor
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPref {
        return SharedPref(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        sharedPref: SharedPref
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY) // TODO for dev purposes only

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(RequestInterceptor(sharedPref))
            .authenticator(ApiAuthenticator(context, sharedPref))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://transformers-api.firebaseapp.com/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /*
    For one time use only in ApiAuthenticator
     */
    fun buildAuthenticatorApiServiceInstance(context: Context, sharedPref: SharedPref): ApiService {
        return provideApiService(
            provideRetrofit(
                provideOkHttpClient(context, sharedPref)
            )
        )
    }
}