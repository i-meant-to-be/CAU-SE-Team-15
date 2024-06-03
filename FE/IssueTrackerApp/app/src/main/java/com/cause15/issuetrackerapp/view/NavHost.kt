package com.cause15.issuetrackerapp.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cause15.issuetrackerapp.view.NavigationItem

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

        }
        composable(route = NavigationItem.IssueListViewItem.route) {

        }
        composable(route = NavigationItem.IssueDetailViewItem.route) {

        }
    }
}