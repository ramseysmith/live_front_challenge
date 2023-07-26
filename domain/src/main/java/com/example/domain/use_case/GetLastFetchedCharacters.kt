package com.example.domain.use_case

import com.example.data.dto.CharacterList
import com.example.domain.repository.CharacterListRepository
import javax.inject.Inject

class GetLastFetchedCharacters @Inject constructor(
    private val characterListRepository: CharacterListRepository
) {
    suspend operator fun invoke(): CharacterList =
        characterListRepository.getLastFetchedCharacters()
}