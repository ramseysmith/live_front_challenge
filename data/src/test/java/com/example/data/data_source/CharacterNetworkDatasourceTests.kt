package com.example.data.data_source

import com.example.data.api.CharacterApi
import com.example.data.dto.CharacterListDto
import com.example.data.dto.Icon
import com.example.data.dto.RelatedTopicsItem
import com.example.data.util.DataTestConstants.MOCK_TEXT
import com.example.data.util.MainCoroutineScopeRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class CharacterNetworkDataSourceTest {

    @get:Rule
    val coroutineScopeRule = MainCoroutineScopeRule()

    @Mock
    private lateinit var characterApi: CharacterApi

    private lateinit var characterNetworkDataSource: CharacterNetworkDatasource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        characterNetworkDataSource =
            CharacterNetworkDatasource(characterApi, coroutineScopeRule.dispatcher)
    }

    @Test
    fun `getCharacterList should return Success when the API call is successful`() =
        runTest(coroutineScopeRule.dispatcher) {
            // Mock successful API response
            val characterListDto = CharacterListDto(
                relatedTopics = listOf(
                    RelatedTopicsItem(
                        text = MOCK_TEXT,
                        icon = Icon(
                            url = "/i/99b04638.png"
                        )
                    )
                )
            )
            val response = Response.success(characterListDto)
            `when`(characterApi.getCharacterList()).thenReturn(response)

            // Invoke the function under test
            val result = characterNetworkDataSource.getCharacterList()

            // Assert the result
            assertEquals(Result.success(characterListDto), result)
        }

    @Test
    fun `getCharacterList should return Failure with the correct error message when the API call is not successful`() =
        runBlocking {
            // Mock unsuccessful API response
            val errorResponseBody = "Error message".toResponseBody(null)
            val response = Response.error<CharacterListDto>(400, errorResponseBody)
            `when`(characterApi.getCharacterList()).thenReturn(response)

            // Invoke the function under test
            val result = characterNetworkDataSource.getCharacterList()

            // Assert the result
            assertEquals(
                Result.failure<Throwable>(Throwable("Response.error()")).toString(),
                result.toString()
            )
        }

    @Test
    fun `getCharacterList should return Failure when a null response is returned`() =
        runTest(coroutineScopeRule.dispatcher) {
            // Mock exception during API call
            val exception = Exception("API call failed")
            given(characterApi.getCharacterList()).willAnswer {
                throw exception
            }
            //    `when`(characterApi.getCharacterList()).thenThrow(exception)

            // Invoke the function under test
            val result = characterNetworkDataSource.getCharacterList()

            // Assert the result
            assertEquals(Result.failure<CharacterListDto>(exception).toString(), result.toString())
        }
}
