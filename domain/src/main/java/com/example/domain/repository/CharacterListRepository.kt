package com.example.domain.repository

import com.example.data.dto.Character
import com.example.data.dto.CharacterList
import kotlinx.coroutines.flow.Flow

interface CharacterListRepository {
    suspend fun getCharacterList(): Flow<Result<CharacterList>>
    suspend fun getLastFetchedCharacters(): Flow<CharacterList>
    suspend fun getCharacterByName(name: String): Flow<Result<Character>>
}