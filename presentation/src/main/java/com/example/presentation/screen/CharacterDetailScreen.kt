package com.example.presentation.screen

import ErrorView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.presentation.contract.CharacterDetailAction
import com.example.presentation.contract.CharacterDetailState
import com.example.presentation.view.CharacterDetailContentView
import com.example.presentation.view.LoadingView
import com.example.presentation.viewmodel.CharacterDetailViewModel

@Composable
fun CharacterDetailScreen(
    characterDetailViewModel: CharacterDetailViewModel = hiltViewModel(),
    navController: NavController,
    characterName: String,
) {
    var firstTime by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = Unit) {
        if (firstTime) {
            characterDetailViewModel.dispatchAction(
                characterDetailAction = CharacterDetailAction.Init(
                    characterName = characterName
                )
            )
            firstTime = false
        }
    }
    CharacterDetailStateHandler(
        state = characterDetailViewModel.characterDetailState.collectAsState().value,
        onBackClickedAction = {
            characterDetailViewModel.dispatchAction(
                characterDetailAction = CharacterDetailAction.BackClickedAction(navController = navController)
            )
        },
        refreshAction = {
            characterDetailViewModel.dispatchAction(
                characterDetailAction = CharacterDetailAction.Refresh(
                    characterName = characterName
                )
            )
        }
    )
}

@Composable
fun CharacterDetailStateHandler(
    state: CharacterDetailState,
    onBackClickedAction: () -> Unit,
    refreshAction: () -> Unit,
) {
    when (state) {
        is CharacterDetailState.CharacterDetailContent -> CharacterDetailContentView(
            characterUiModel = state.character,
            backClickedAction = onBackClickedAction
        )

        is CharacterDetailState.Error -> ErrorView(
            onRetryClick = refreshAction
        )

        CharacterDetailState.Loading -> LoadingView()
    }
}