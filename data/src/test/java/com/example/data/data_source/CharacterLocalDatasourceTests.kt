package com.example.data.data_source

import com.example.data.db.CharacterDao
import com.example.data.dto.Character
import com.example.data.dto.CharacterDatabaseDto
import com.example.data.dto.CharacterList
import com.example.data.util.MainCoroutineScopeRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class CharacterLocalDatasourceTest {

    @get:Rule
    val coroutineScopeRule = MainCoroutineScopeRule()

    @Mock
    private lateinit var characterDao: CharacterDao

    private lateinit var characterLocalDatasource: CharacterLocalDatasource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        characterLocalDatasource =
            CharacterLocalDatasource(characterDao, coroutineScopeRule.dispatcher)
    }

    @Test
    fun `storeCharacters should insert characters into the database`() =
        runTest(coroutineScopeRule.dispatcher) {
            // Arrange
            val characterList = CharacterList(
                listOf(
                    Character("Character 1", "Description 1", "Image URL 1"),
                    Character("Character 2", "Description 2", "Image URL 2")
                )
            )
            val characterDatabaseList = characterList.characters.map {
                CharacterDatabaseDto(
                    name = it.title,
                    description = it.description,
                    imageUrl = it.imageUrl
                )
            }

            // Act
            characterLocalDatasource.storeCharacters(characterList)

            // Assert
            verify(characterDao).insertCharacters(characterDatabaseList)
        }

    @Test
    fun `retrieveCharacters should return characters from the database`() =
        runTest(coroutineScopeRule.dispatcher) {
            // Arrange
            val characterDatabaseList = listOf(
                CharacterDatabaseDto(
                    name = "Character 1",
                    description = "Description 1",
                    imageUrl = "Image URL 1"
                ),
                CharacterDatabaseDto(
                    name = "Character 2",
                    description = "Description 2",
                    imageUrl = "Image URL 2"
                )
            )
            val expectedCharacterList = CharacterList(
                listOf(
                    Character("Character 1", "Description 1", "Image URL 1"),
                    Character("Character 2", "Description 2", "Image URL 2")
                )
            )
            `when`(characterDao.getCharacterList()).thenReturn(characterDatabaseList)

            // Act
            val result = characterLocalDatasource.retrieveCharacters()

            // Assert
            assertEquals(expectedCharacterList, result)
        }

    @Test
    fun `getCharacterByName should return the character with the given name from the database`() =
        runTest(coroutineScopeRule.dispatcher) {
            // Arrange
            val characterName = "Character 1"
            val characterDatabase =
                CharacterDatabaseDto(
                    name = characterName,
                    description = "Description 1",
                    imageUrl = "Image URL 1"
                )
            val expectedCharacter = Character(characterName, "Description 1", "Image URL 1")
            `when`(characterDao.getCharacter(characterName)).thenReturn(listOf(characterDatabase))

            // Act
            val result = characterLocalDatasource.getCharacterByName(characterName)

            // Assert
            assertEquals(Result.success(expectedCharacter), result)
        }

    @Test
    fun `getCharacterByName should return Failure when no character with the given name is found in the database`() =
        runTest(coroutineScopeRule.dispatcher) {
            // Arrange
            val characterName = "Non-existent Character"
            `when`(characterDao.getCharacter(characterName)).thenReturn(emptyList())

            // Act
            val result = characterLocalDatasource.getCharacterByName(characterName)

            // Assert
            assertEquals(
                Result.failure<Character>(Throwable("Null Character!")).toString(),
                result.toString()
            )
        }

    @Test
    fun `deleteAllCharactersFromDatabase should delete all characters from the database`() =
        runTest(coroutineScopeRule.dispatcher) {
            // Act
            characterLocalDatasource.deleteAllCharactersFromDatabase()

            // Assert
            verify(characterDao).deleteAllCharacters()
        }
}
