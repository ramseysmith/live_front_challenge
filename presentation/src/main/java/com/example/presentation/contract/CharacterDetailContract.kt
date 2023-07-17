package com.example.presentation.contract

import androidx.navigation.NavController

sealed class CharacterDetailState {
    object Loading : CharacterDetailState()
    object Error : CharacterDetailState()
    class CharacterDetailContent(
        val character: CharacterUiModel
    ) : CharacterDetailState()
}

sealed class CharacterDetailAction {
    class Init(
        val characterName: String
    ) : CharacterDetailAction()

    class BackClickedAction(
        val navController: NavController
    ) : CharacterDetailAction()
}

