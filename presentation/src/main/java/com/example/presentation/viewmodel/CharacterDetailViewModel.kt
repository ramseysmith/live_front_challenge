package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.domain.use_case.GetCharacterByName
import com.example.presentation.contract.CharacterDetailAction
import com.example.presentation.contract.CharacterDetailState
import com.example.presentation.contract.CharacterUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterByName: GetCharacterByName
) : ViewModel() {

    private val _characterDetailState: MutableStateFlow<CharacterDetailState> =
        MutableStateFlow(CharacterDetailState.Loading)
    val characterDetailState: StateFlow<CharacterDetailState> = _characterDetailState

    fun dispatchAction(characterDetailAction: CharacterDetailAction) {
        when (characterDetailAction) {
            is CharacterDetailAction.Init -> processInit(characterName = characterDetailAction.characterName)
            is CharacterDetailAction.BackClickedAction -> processBackClickedAction(navController = characterDetailAction.navController)
            is CharacterDetailAction.Refresh -> processRefresh(characterName = characterDetailAction.characterName)
        }
    }

    private fun processRefresh(characterName: String) {
        _characterDetailState.tryEmit(CharacterDetailState.Loading)
        loadCharacterDetailsAndUpdateState(characterName)
    }

    private fun processBackClickedAction(navController: NavController) {
        navController.popBackStack()
    }

    private fun processInit(characterName: String) {
        loadCharacterDetailsAndUpdateState(characterName)
    }

    private fun loadCharacterDetailsAndUpdateState(characterName: String) {
        viewModelScope.launch {
            getCharacterByName(name = characterName).fold(
                onFailure = {
                    _characterDetailState.tryEmit(
                        CharacterDetailState.Error(failedCharacterName = characterName)
                    )
                },
                onSuccess = { character ->
                    _characterDetailState.tryEmit(
                        CharacterDetailState.CharacterDetailContent(
                            character = CharacterUiModel(
                                name = character.title,
                                shortDescription = character.description,
                                imageUrl = character.imageUrl
                            )
                        )
                    )
                }
            )
        }
    }
}