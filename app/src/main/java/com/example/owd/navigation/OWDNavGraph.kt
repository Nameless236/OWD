package com.example.owd.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.owd.screens.AddGroup
import com.example.owd.screens.AddGroupBackground
import com.example.owd.screens.Expenses
import com.example.owd.screens.ExpensesScreen
import com.example.owd.screens.GroupsDest
import com.example.owd.screens.MainScreen

@Composable
fun OwdNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = GroupsDest.route,
        modifier = modifier
    )
    {
        composable(route = GroupsDest.route) {
            MainScreen(
                navigateToAddGroup = {navController.navigate(AddGroup.route)},
                navigateToGroupDetails = {}
            )
        }
        composable(route = AddGroup.route) {
            AddGroupBackground(
                navigateBack = {
                    navController.popBackStack()
                    navController.navigate(GroupsDest.route)
                    }
            )
        }
        composable(route = Expenses.route) {
            ExpensesScreen();
        }
    }
}