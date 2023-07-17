package com.example.domain.di

import android.content.SharedPreferences
import com.example.data.data_source.CharacterNetworkDatasource
import com.example.domain.repository.CharacterListRepositoryImpl
import com.example.domain.repository.CharacterListRepository
import com.example.data.data_source.CharacterLocalDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CharacterListRepositoryModule {

    @Provides
    @Singleton
    fun provideCharacterListRepository(
        characterNetworkDatasource: CharacterNetworkDatasource,
        characterLocalDatasource: CharacterLocalDatasource,
        sharedPreferences: SharedPreferences
    ): CharacterListRepository = CharacterListRepositoryImpl(
        characterNetworkDatasource = characterNetworkDatasource,
        characterLocalDatasource = characterLocalDatasource,
        sharedPreferences = sharedPreferences
    )
}