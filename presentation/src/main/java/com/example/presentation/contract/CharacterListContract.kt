package com.example.presentation.contract

import androidx.navigation.NavController


sealed class CharacterListState {
    object Loading : CharacterListState()
    object Error : CharacterListState()
    data class Content(
        val characters: List<CharacterUiModel>
    ) : CharacterListState()
}

sealed class CharacterListAction {
    data class CharacterClickedAction(
        val navController: NavController,
        val name: String
    ) : CharacterListAction()

    object Refresh: CharacterListAction()
}

data class CharacterUiModel(
    val name: String,
    val shortDescription: String,
    val imageUrl: String,
)
