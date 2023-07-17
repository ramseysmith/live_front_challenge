package com.example.livefrontchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.livefrontchallenge.ui.theme.LiveFrontChallengeTheme
import com.example.presentation.screen.CharacterDetailScreen
import com.example.presentation.screen.CharacterListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiveFrontChallengeTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                navController.enableOnBackPressed(true)
                NavHost(navController = navController, startDestination = "character_list") {
                    composable("character_list") {
                        CharacterListScreen(navController = navController)
                    }
                    composable(
                        route = "character_detail/{characterName}",
                        arguments = listOf(navArgument("characterName") {
                            type = NavType.StringType
                        })
                    ) { backStackEntry ->
                        val name = backStackEntry.arguments?.getString("characterName")
                        CharacterDetailScreen(
                            characterName = name ?: "",
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}