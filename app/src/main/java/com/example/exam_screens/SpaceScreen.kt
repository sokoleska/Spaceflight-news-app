package com.example.exam_screens;

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.exam_screens.data.ArticleRepository
import com.example.exam_screens.data.DetailsViewModelFactory
import com.example.exam_screens.ui.components.CustomBottomBar
import com.example.exam_screens.ui.screens.DetailsScreen
import com.example.exam_screens.ui.screens.FavoritesScreen
import com.example.exam_screens.ui.screens.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpaceApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            CustomBottomBar(
                currentRoute = currentRoute,
                onItemClick = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Favorites.route) {
                FavoritesScreen(navController)
            }
            composable(Screen.Details.route) { backStackEntry ->
                val repository = ArticleRepository()
                val articleId = backStackEntry.arguments?.getString("articleId")?.toIntOrNull()
                    ?: throw IllegalArgumentException("Missing articleId")

                DetailsScreen(
                    articleId = articleId,
                    viewModel = viewModel(factory = DetailsViewModelFactory(articleId, repository)),
                    navController = navController
                )
            }
        }

    }
}

sealed class Screen(
    val route: String,
    @StringRes val title: Int,
    val icon: ImageVector
) {
    object Home : Screen("home", R.string.home, Icons.Default.Home)
    object Favorites : Screen("favorites", R.string.favorites, Icons.Default.Favorite)
    object Details : Screen("details/{articleId}", R.string.details, Icons.Default.Info)
}