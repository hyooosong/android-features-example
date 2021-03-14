package com.example.hilt_di.di

import com.example.hilt_di.di.qualifier.AppHash
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule{

    @AppHash
    @Provides
    fun provideHash() = hashCode().toString()
}