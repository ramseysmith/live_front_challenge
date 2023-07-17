package com.example.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.presentation.contract.CharacterListAction
import com.example.presentation.contract.CharacterListState
import com.example.presentation.view.CharacterListContentView
import com.example.presentation.view.CharacterListErrorView
import com.example.presentation.view.CharacterListLoadingView
import com.example.presentation.viewmodel.CharacterListViewModel

@Composable
fun CharacterListScreen(
    characterListViewModel: CharacterListViewModel = hiltViewModel(),
    navController: NavController,
) {
    CharacterListStateHandler(
        state = characterListViewModel.characterListState.collectAsState().value,
        characterClickedAction = {
            characterListViewModel.dispatchAction(
                characterListAction = CharacterListAction.CharacterClickedAction(
                    navController = navController,
                    name = it
                )
            )
        }
    )
}

@Composable
fun CharacterListStateHandler(
    state: CharacterListState,
    characterClickedAction: (name: String) -> Unit
) {
    when (state) {
        is CharacterListState.Content -> CharacterListContentView(
            characters = state.characters,
            characterClickedAction = characterClickedAction
        )

        CharacterListState.Error -> CharacterListErrorView()
        CharacterListState.Loading -> CharacterListLoadingView()
    }
}