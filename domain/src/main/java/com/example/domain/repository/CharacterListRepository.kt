package com.example.domain.repository

import com.example.data.dto.Character
import com.example.data.dto.CharacterList

interface CharacterListRepository {
    suspend fun getCharacterList(): Result<CharacterList>
    suspend fun getLastFetchedCharacters(): CharacterList
    suspend fun getCharacterByName(name: String): Result<Character>
}