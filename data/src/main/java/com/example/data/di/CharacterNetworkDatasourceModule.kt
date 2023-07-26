package com.example.data.di

import com.example.data.api.CharacterApi
import com.example.data.data_source.CharacterNetworkDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CharacterNetworkDatasourceModule {

    @Provides
    @Singleton
    fun provideCharacterListNetworkDatasource(
        characterApi: CharacterApi,
    ): CharacterNetworkDatasource = CharacterNetworkDatasource(
        characterApi = characterApi,
    )
}