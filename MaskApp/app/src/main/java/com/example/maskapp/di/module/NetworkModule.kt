package com.example.maskapp.di.module

import com.example.maskapp.network.MaskService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun <T> provideService(clazz: Class<T>): T = Retrofit.Builder()
        .baseUrl(MaskService.MaskServiceBaseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpLoggingClient())
        .build()
        .create(clazz)

    private fun httpLoggingClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }
}