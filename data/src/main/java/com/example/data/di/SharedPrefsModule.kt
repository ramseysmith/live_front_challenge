package com.example.data.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPrefsModule {

    @Provides
    @Singleton
    fun provideSharedPrefs(
        application: Application
    ): SharedPreferences = application.getSharedPreferences("timestamp", Context.MODE_PRIVATE)
}