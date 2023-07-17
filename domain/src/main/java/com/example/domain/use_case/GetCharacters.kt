package com.example.domain.use_case

import com.example.data.dto.CharacterList
import com.example.domain.repository.CharacterListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacters @Inject constructor(
    private val characterListRepository: CharacterListRepository
) {
    suspend operator fun invoke(): Flow<Result<CharacterList>> =
        characterListRepository.getCharacterList()
}