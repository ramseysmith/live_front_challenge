package com.example.presentation.viewmodel

import androidx.navigation.NavController
import app.cash.turbine.test
import com.example.data.dto.CharacterList
import com.example.domain.use_case.GetCharacters
import com.example.presentation.MainCoroutineScopeRule
import com.example.presentation.contract.CharacterListAction
import com.example.presentation.contract.CharacterListState
import com.example.presentation.contract.CharacterUiModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterListViewModelTest {

    @get:Rule
    val coroutineScopeRule = MainCoroutineScopeRule()

    @RelaxedMockK
    private lateinit var getCharacters: GetCharacters

    @RelaxedMockK
    private lateinit var navController: NavController

    private lateinit var viewModel: CharacterListViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `viewModelScope launch should emit Loading state initially`() {
        // Arrange
        val expectedInitialState = CharacterListState.Loading
        viewModel = CharacterListViewModel(getCharacters)
        // Assert
        assertEquals(expectedInitialState, viewModel.characterListState.value)
    }

    @Test
    fun `viewModelScope launch should emit Content state when characters are retrieved successfully`() {
        runTest(coroutineScopeRule.dispatcher) {
            // Arrange
            val characterList = createCharacterList()
            val expectedContentState = CharacterListState.Content(
                characters = characterList.characters.map { character ->
                    CharacterUiModel(
                        name = character.title,
                        shortDescription = character.description,
                        imageUrl = character.imageUrl
                    )
                }
            )
            coEvery { getCharacters() } returns (Result.success(characterList))
            viewModel = CharacterListViewModel(getCharacters)
            // Act
            viewModel.characterListState.test {
                assertEquals(expectedContentState, awaitItem())
            }
        }
    }

    @Test
    fun `viewModelScope launch should emit Error state when characters retrieval fails`() {
        runTest(coroutineScopeRule.dispatcher) {
            // Arrange
            val expectedErrorState = CharacterListState.Error
            coEvery { getCharacters() } returns Result.failure(Throwable("Error"))
            viewModel = CharacterListViewModel(getCharacters)
            // Act
            viewModel.characterListState.test {
                assertEquals(expectedErrorState, awaitItem())
            }
        }
    }

    @Test
    fun `dispatchAction with CharacterClickedAction should call navigate on NavController`() {
        // Arrange
        val characterName = "Character 1"
        val characterClickedAction = CharacterListAction.CharacterClickedAction(
            navController = navController,
            name = characterName
        )
        viewModel = CharacterListViewModel(getCharacters)
        // Act
        viewModel.dispatchAction(characterClickedAction)

        // Verify that NavController.navigate was called with the expected route and capture the lambda argument
        verify(exactly = 1) {
            navController.navigate(
                eq("character_detail/$characterName"),
                captureLambda()
            )
        }
    }


    private fun createCharacterList(): CharacterList {
        val characters = listOf(
            createCharacter("Character 1"),
            createCharacter("Character 2"),
            createCharacter("Character 3")
        )
        return CharacterList(characters)
    }

    private fun createCharacter(name: String): com.example.data.dto.Character {
        return com.example.data.dto.Character(name, "Description", "Image URL")
    }
}
