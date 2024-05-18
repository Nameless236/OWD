package com.example.owd.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.owd.screens.AddGroup
import com.example.owd.screens.AddGroupBackground
import com.example.owd.screens.HomeDestination
import com.example.owd.screens.MainScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun OwdNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    )
    {
        composable(route = HomeDestination.route) {
            MainScreen(
                navigateToAddGroup =
                { navController.navigate(AddGroup.route) },
                navigateToGroupDetails =
                { navController.navigate(AddGroup.route) })
        }


        composable(route = AddGroup.route) {
            AddGroupBackground(modifier = Modifier)
        }

    }
}