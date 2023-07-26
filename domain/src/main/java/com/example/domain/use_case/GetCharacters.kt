package com.example.domain.use_case

import com.example.data.dto.CharacterList
import com.example.domain.repository.CharacterListRepository
import javax.inject.Inject

class GetCharacters @Inject constructor(
    private val characterListRepository: CharacterListRepository
) {
    suspend operator fun invoke(): Result<CharacterList> =
        characterListRepository.getCharacterList()
}