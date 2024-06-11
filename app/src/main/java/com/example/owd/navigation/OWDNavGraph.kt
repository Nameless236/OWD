package com.example.owd.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.owd.AppViewModelProvider
import com.example.owd.screens.AddExpenseDest
import com.example.owd.screens.AddExpenseScreen
import com.example.owd.screens.AddGroup
import com.example.owd.screens.AddGroupBackground
import com.example.owd.screens.GroupDetailScreen
import com.example.owd.screens.GroupDetailsDest
import com.example.owd.screens.GroupsDest
import com.example.owd.screens.MainScreen
import com.example.owd.viewModels.GroupDetailsViewModel

@Composable
fun OwdNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val myNavController = remember { navController }
    NavHost(
        navController = myNavController,
        startDestination = GroupsDest.route,
        modifier = modifier
    )
    {
        composable(route = GroupsDest.route) {
            MainScreen(
                navigateToAddGroup = { navController.navigate(AddGroup.route) },
                navigateToGroupDetails = { groupId ->
                    navController.navigate("${GroupDetailsDest.route}/$groupId")
                }
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

        composable(
            route = GroupDetailsDest.routeWithArgs,
            arguments = listOf(navArgument("groupId") {
                type = NavType.LongType
            })
        ) {
            val groupsViewModel: GroupDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
            val groupId = it.arguments?.getLong("groupId") ?: 0L
            LaunchedEffect(key1 = groupId) { // Key the effect to groupId
                val previousGroupId = groupsViewModel.uiState.value.group.id
                if (groupId != previousGroupId) {
                    groupsViewModel.setGroupId(groupId)
                }
            }

            GroupDetailScreen(
                navigateToAddExpense = { navController.navigate(AddExpenseDest.route) },
                navigateToHome = {
                    navController.popBackStack()
                    navController.navigate(GroupsDest.route)
                },
                viewModel = groupsViewModel
            )
        }

        composable(
            route = AddExpenseDest.route
        ) {
            val groupId = navController.previousBackStackEntry?.arguments?.getLong("groupId") ?: 0L
            val groupsViewModel: GroupDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)

            LaunchedEffect(key1 = groupId) { // Key the effect to groupId
                val previousGroupId = groupsViewModel.uiState.value.group.id
                if (groupId != previousGroupId) {
                    groupsViewModel.setGroupId(groupId)
                }
            }

            AddExpenseScreen(
                viewModel = groupsViewModel,
                navigateBack = {
                    navController.popBackStack()
                    navController.navigate("${GroupDetailsDest.route}/$groupId")
                }
            )
        }
    }
}
