package com.example.domain.use_case

import com.example.data.dto.Character
import com.example.domain.repository.CharacterListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterByName @Inject constructor(
    private val characterListRepository: CharacterListRepository
) {
    suspend operator fun invoke(name: String): Flow<Result<Character>> {
        return characterListRepository.getCharacterByName(name = name)
    }
}