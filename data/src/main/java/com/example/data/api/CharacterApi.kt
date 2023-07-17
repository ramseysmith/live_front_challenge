package com.example.data.api

import com.example.data.constants.DataConstants.SERVER_URL
import com.example.data.dto.CharacterListDto
import retrofit2.Response
import retrofit2.http.GET

interface CharacterApi {
    @GET(SERVER_URL)
    suspend fun getCharacterList() : Response<CharacterListDto>
}