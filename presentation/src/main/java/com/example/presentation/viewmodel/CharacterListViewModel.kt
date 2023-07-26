package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.domain.use_case.GetCharacters
import com.example.presentation.contract.CharacterListAction
import com.example.presentation.contract.CharacterListState
import com.example.presentation.contract.CharacterUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharacters: GetCharacters
) : ViewModel() {

    private val _characterListState: MutableStateFlow<CharacterListState> =
        MutableStateFlow(CharacterListState.Loading)
    val characterListState: StateFlow<CharacterListState> = _characterListState

    init {
        viewModelScope.launch {
            getCharacters().fold(
                onFailure = {
                    _characterListState.tryEmit(CharacterListState.Error)
                },
                onSuccess = { characterList ->
                    _characterListState.tryEmit(
                        CharacterListState.Content(
                            characters = characterList.characters.map { character ->
                                CharacterUiModel(
                                    name = character.title,
                                    shortDescription = character.description,
                                    imageUrl = character.imageUrl
                                )
                            }
                        )
                    )
                }
            )

        }
    }

    fun dispatchAction(characterListAction: CharacterListAction) {
        when (characterListAction) {
            is CharacterListAction.CharacterClickedAction -> processCharacterClicked(
                navController = characterListAction.navController,
                name = characterListAction.name
            )
        }
    }

    private fun processCharacterClicked(navController: NavController, name: String) {
        navController.navigate("character_detail/$name") {
            popUpTo("character_list") { inclusive = false }
        }
    }
}