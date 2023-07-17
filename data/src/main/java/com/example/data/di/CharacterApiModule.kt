package com.example.data.di

import com.example.data.api.CharacterApi
import com.example.data.constants.DataConstants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class CharacterApiModule {

    @Provides
    @Singleton
    fun provideCharacterListApi(): CharacterApi {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CharacterApi::class.java)
    }
}