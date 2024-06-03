package com.cause15.issuetrackerapp.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cause15.issuetrackerapp.data.model.User

@Composable
fun NavigationRoot(
    modifier: Modifier
) {
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = NavigationItem.LoginViewItem.route,
        modifier = modifier
    ) {
        composable(route = NavigationItem.LoginViewItem.route) {
            LoginView(navHostController = navHostController)
        }
        composable(
            route = NavigationItem.IssueListViewItem.route + "/{user_id}",
            arguments = listOf(navArgument("user_id") { type = NavType.StringType })
        ) {
            val userId = it.arguments?.getString("user_id")
            IssueListView(
                userId = userId ?: "",
                navHostController = navHostController
            )
        }
        composable(
            route = NavigationItem.IssueDetailViewItem.route + "/{issue_id}",
            arguments = listOf(navArgument("issue_id") { type = NavType.StringType })
        ) {
            val issueId = it.arguments?.getString("issue_id")
            IssueDetailView(
                issueId = issueId ?: "",
                navHostController = navHostController
            )
        }
    }
}