package com.example.data.data_source

import com.example.data.api.CharacterApi
import com.example.data.constants.DataConstants.BASE_URL
import com.example.data.constants.DataConstants.SERVER_URL
import com.example.data.di.IoDispatcher
import com.example.data.dto.CharacterListDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CharacterNetworkDatasource(
    private val characterApi: CharacterApi,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
) {
    suspend fun getCharacterList(): Result<CharacterListDto> {
        return try {
            withContext(coroutineDispatcher) {
                val response = characterApi.getCharacterList()
                if (response.isSuccessful) {
                    response.body()?.let { Result.success(it) }
                        ?: Result.failure(Throwable("Null response body returned from: $BASE_URL$SERVER_URL!"))
                } else {
                    Result.failure(Throwable(response.message()))
                }
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}