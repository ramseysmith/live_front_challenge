package com.example.domain.repository

import android.content.SharedPreferences
import com.example.data.constants.DataConstants.CHARACTER_UPDATE_THRESHOLD
import com.example.data.constants.DataConstants.LAST_CHARACTER_UPDATE_KEY
import com.example.data.data_source.CharacterLocalDatasource
import com.example.data.data_source.CharacterNetworkDatasource
import com.example.data.dto.Character
import com.example.data.dto.CharacterList
import com.example.data.dto.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharacterListRepositoryImpl(
    private val characterNetworkDatasource: CharacterNetworkDatasource,
    private val characterLocalDatasource: CharacterLocalDatasource,
    private val sharedPreferences: SharedPreferences
) : CharacterListRepository {
    override suspend fun getCharacterList(): Flow<Result<CharacterList>> {
        return flow { emit(acquireCharacters()) }
    }

    override suspend fun getLastFetchedCharacters(): Flow<CharacterList> {
        return flow { emit(characterLocalDatasource.retrieveCharacters()) }
    }

    override suspend fun getCharacterByName(name: String): Flow<Result<Character>> {
        return flow { emit(characterLocalDatasource.getCharacterByName(name = name)) }
    }

    private suspend fun acquireCharacters(): Result<CharacterList> {
        val currentTime = (System.currentTimeMillis() / 1000).toInt()
        val timeOfLastUpdate = sharedPreferences.getInt(LAST_CHARACTER_UPDATE_KEY, 0)
        return if (currentTime - timeOfLastUpdate >= CHARACTER_UPDATE_THRESHOLD) {
            characterNetworkDatasource.getCharacterList().fold(
                onFailure = { Result.failure(it) },
                onSuccess = { dto ->
                    val characterList = dto.toDomain()
                    characterList.characters.distinctBy { it.title }.let {
                        if (characterList.characters.isNotEmpty()) {
                            characterLocalDatasource.deleteAllCharactersFromDatabase()
                            characterLocalDatasource.storeCharacters(
                                characterList = characterList
                            )
                            sharedPreferences
                                .edit()
                                .putInt(
                                    LAST_CHARACTER_UPDATE_KEY,
                                    (System.currentTimeMillis() / 1000).toInt()
                                )
                                .apply()
                            Result.success(characterList)
                        } else {
                            Result.failure(Throwable("Empty character list returned by "))
                        }
                    }

                }
            )
        } else {
            val storedCharacters = characterLocalDatasource.retrieveCharacters()
            if (storedCharacters.characters.isNotEmpty()) {
                Result.success(storedCharacters)
            } else {
                Result.failure(Throwable("Empty set of stored characters!"))
            }
        }
    }
}