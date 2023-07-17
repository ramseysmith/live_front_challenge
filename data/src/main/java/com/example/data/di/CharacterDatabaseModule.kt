package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.CharacterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class CharacterDatabaseModule {

    @Provides
    fun provideCharacterDatabase(
        @ApplicationContext context: Context
    ): CharacterDatabase = Room.databaseBuilder(
        context,
        CharacterDatabase::class.java,
        "character"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideCharacterDao(
        characterDataBase: CharacterDatabase
    ) = characterDataBase.characterDao()
}