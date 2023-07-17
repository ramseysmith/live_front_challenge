package com.example.data.data_source

import com.example.data.db.CharacterDao
import com.example.data.di.IoDispatcher
import com.example.data.dto.Character
import com.example.data.dto.CharacterDatabaseDto
import com.example.data.dto.CharacterList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CharacterLocalDatasource(
    private val characterDao: CharacterDao,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
) {
    suspend fun storeCharacters(characterList: CharacterList) {
        withContext(coroutineDispatcher) {
            characterDao.insertCharacters(
                characters = characterList.characters.map {
                    CharacterDatabaseDto(
                        name = it.title,
                        description = it.description,
                        imageUrl = it.imageUrl
                    )
                }
            )
        }
    }

    suspend fun retrieveCharacters(): CharacterList {
        return withContext(coroutineDispatcher) {
            CharacterList(
                characters = characterDao.getCharacterList().map {
                    Character(
                        title = it.name,
                        description = it.description,
                        imageUrl = it.imageUrl
                    )
                }
            )
        }
    }

    suspend fun getCharacterByName(name: String): Result<Character> {
        return withContext(coroutineDispatcher) {
            val result = characterDao.getCharacter(name = name).firstOrNull()
            if (result != null) {
                Result.success(
                    Character(
                        title = result.name,
                        description = result.description,
                        imageUrl = result.imageUrl
                    )
                )
            } else {
                Result.failure(Throwable("Null Character!"))
            }
        }
    }

    suspend fun deleteAllCharactersFromDatabase() {
        withContext(coroutineDispatcher) { characterDao.deleteAllCharacters() }
    }
}