package com.example.domain.repository

import android.content.SharedPreferences
import com.example.data.data_source.CharacterLocalDatasource
import com.example.data.data_source.CharacterNetworkDatasource
import com.example.data.dto.Character
import com.example.data.dto.CharacterList
import com.example.data.dto.CharacterListDto
import com.example.data.dto.Icon
import com.example.data.dto.RelatedTopicsItem
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterListRepositoryImplTest {

    @RelaxedMockK
    private lateinit var characterNetworkDatasource: CharacterNetworkDatasource

    @RelaxedMockK
    private lateinit var characterLocalDatasource: CharacterLocalDatasource

    @RelaxedMockK
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var characterListRepository: CharacterListRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        characterListRepository =
            CharacterListRepositoryImpl(
                characterNetworkDatasource,
                characterLocalDatasource,
                sharedPreferences
            )
    }

    @Test
    fun `getCharacterList should return acquired characters from network`() = runTest {
        // Arrange
        val characterList = createCharacterList()
        val expectedResult = Result.success(characterList)
        coEvery { sharedPreferences.getInt(any(), any()) } returns 0
        coEvery { characterNetworkDatasource.getCharacterList() } returns Result.success(
            createCharacterListDto()
        )

        // Act
        val result = characterListRepository.getCharacterList()

        // Assert
        assertEquals(expectedResult, result)
        coVerify(exactly = 1) {
            characterNetworkDatasource.getCharacterList()
            characterLocalDatasource.deleteAllCharactersFromDatabase()
            characterLocalDatasource.storeCharacters(characterList)
        }
        coVerify(exactly = 0) { characterLocalDatasource.retrieveCharacters() }
    }

    @Test
    fun `getCharacterList should return stored characters if not expired`() = runTest {
        // Arrange
        val storedCharacterList = createCharacterList()
        coEvery {
            sharedPreferences.getInt(
                any(),
                any()
            )
        } returns (System.currentTimeMillis() / 1000).toInt()
        coEvery { characterLocalDatasource.retrieveCharacters() } returns storedCharacterList

        // Act
        val result = characterListRepository.getCharacterList()

        // Assert
        assertEquals(Result.success(storedCharacterList), result)
        coVerify(exactly = 0) {
            characterNetworkDatasource.getCharacterList()
            characterLocalDatasource.deleteAllCharactersFromDatabase()
            characterLocalDatasource.storeCharacters(any())
            sharedPreferences.edit().putInt(any(), any()).apply()
        }
        coVerify(exactly = 1) { characterLocalDatasource.retrieveCharacters() }
    }

    @Test
    fun `getCharacterList should return return failure if network call fails`() = runTest {
        // Arrange
        val storedCharacterList = createCharacterList()
        val expectedResult = Result.failure<CharacterListDto>(Throwable("Network error"))
        coEvery { sharedPreferences.getInt(any(), any()) } returns 0
        coEvery { characterNetworkDatasource.getCharacterList() } returns expectedResult
        // Act
        val result = characterListRepository.getCharacterList()

        // Assert
        assertEquals(
            Result.failure<CharacterListDto>(Throwable("Network error")).toString(),
            result.toString()
        )
        coVerify(exactly = 1) {
            characterNetworkDatasource.getCharacterList()
        }
    }

    @Test
    fun `getLastFetchedCharacters should return stored characters`() = runTest {
        // Arrange
        val storedCharacterList = createCharacterList()
        coEvery { characterLocalDatasource.retrieveCharacters() } returns storedCharacterList

        // Act
        val result = characterListRepository.getLastFetchedCharacters()

        // Assert
        assertEquals(storedCharacterList, result)
        coVerify(exactly = 1) { characterLocalDatasource.retrieveCharacters() }
        coVerify(exactly = 0) { characterNetworkDatasource.getCharacterList() }
        coVerify(exactly = 0) { characterLocalDatasource.deleteAllCharactersFromDatabase() }
        coVerify(exactly = 0) { characterLocalDatasource.storeCharacters(any()) }
        coVerify(exactly = 0) { sharedPreferences.edit().putInt(any(), any()).apply() }
    }

    @Test
    fun `getCharacterByName should return character by name from local datasource`() = runTest {
        // Arrange
        val characterName = "Character 1"
        val storedCharacter = createCharacter(characterName)
        val expectedResult = Result.success(storedCharacter)
        coEvery { characterLocalDatasource.getCharacterByName(characterName) } returns expectedResult
        // Act
        val result = characterListRepository.getCharacterByName(characterName)
        // Assert
        assertEquals(expectedResult, result)
        coVerify(exactly = 1) { characterLocalDatasource.getCharacterByName(characterName) }
        coVerify(exactly = 0) { characterNetworkDatasource.getCharacterList() }
        coVerify(exactly = 0) { characterLocalDatasource.deleteAllCharactersFromDatabase() }
        coVerify(exactly = 0) { characterLocalDatasource.storeCharacters(any()) }
        coVerify(exactly = 0) { sharedPreferences.edit().putInt(any(), any()).apply() }
    }

    private fun createCharacterList(): CharacterList {
        val characters = listOf(
            createCharacter("Character 1"),
            createCharacter("Character 2"),
            createCharacter("Character 3")
        )
        return CharacterList(characters)
    }

    private fun createCharacterListDto(): CharacterListDto {
        val relatedTopics = listOf(
            RelatedTopicsItem(
                text = "Character 1 - Description",
                icon = Icon(
                    url = "Image URL"
                )
            ),
            RelatedTopicsItem(
                text = "Character 2 - Description",
                icon = Icon(
                    url = "Image URL"
                )
            ),
            RelatedTopicsItem(
                text = "Character 3 - Description",
                icon = Icon(
                    url = "Image URL"
                )
            )
        )
        return CharacterListDto(relatedTopics = relatedTopics)
    }

    private fun createCharacter(name: String): Character {
        return Character(name, "Description", "https://duckduckgo.comImage URL ")
    }
}
