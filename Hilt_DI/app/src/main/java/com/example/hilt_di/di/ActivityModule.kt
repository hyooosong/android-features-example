package com.example.hilt_di.di

import com.example.hilt_di.di.qualifier.ActivityHash
import com.example.hilt_di.di.qualifier.AppHash
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object ActivityModule {

    @ActivityHash
    @Provides
    fun provideHash() = hashCode().toString()
}