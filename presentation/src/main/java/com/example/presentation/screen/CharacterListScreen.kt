package com.example.presentation.screen

import ErrorView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.presentation.contract.CharacterListAction
import com.example.presentation.contract.CharacterListState
import com.example.presentation.view.CharacterListContentView
import com.example.presentation.view.LoadingView
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
        },
        refreshAction = {
            characterListViewModel.dispatchAction(
                characterListAction = CharacterListAction.Refresh
            )
        }
    )
}

@Composable
fun CharacterListStateHandler(
    state: CharacterListState,
    characterClickedAction: (name: String) -> Unit,
    refreshAction: () -> Unit,
) {
    when (state) {
        is CharacterListState.Content -> CharacterListContentView(
            characters = state.characters,
            characterClickedAction = characterClickedAction
        )

        CharacterListState.Error -> ErrorView(
            onRetryClick = refreshAction,
        )

        CharacterListState.Loading -> LoadingView()
    }
}