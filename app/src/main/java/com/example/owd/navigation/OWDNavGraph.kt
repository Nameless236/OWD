package com.example.owd.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.owd.screens.AddExpense
import com.example.owd.screens.AddExpenseScreen
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
                navigateToGroupDetails = {navController.navigate("${Expenses.route}/${it}")}
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
            ExpensesScreen(
                navigateToAddExpense = {navController.navigate(AddExpense.route)}
            );
        }

        composable(
            route = Expenses.routeWithArgs,
            arguments = listOf(navArgument(Expenses.groupId) {
                type = NavType.LongType
            })
        ) {
            ExpensesScreen(navigateToAddExpense = {navController.navigate(AddExpense.route)})
        }

        composable(route = AddExpense.route) {
            AddExpenseScreen()
        }
    }
}