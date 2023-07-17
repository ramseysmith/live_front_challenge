package com.example.data.di

import com.example.data.data_source.CharacterLocalDatasource
import com.example.data.db.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CharacterLocalDatasourceModule {
    @Provides
    @Singleton
    fun provideCharacterLocalDatasource(
        characterDao: CharacterDao,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): CharacterLocalDatasource =
        CharacterLocalDatasource(
            characterDao = characterDao,
            coroutineDispatcher = coroutineDispatcher
        )
}