package com.cause15.issuetrackerapp.view

sealed class NavigationItem(
    val route: String
) {
    data object LoginViewItem: NavigationItem("/login")
    data object IssueListViewItem: NavigationItem("/issue_list")
    data object IssueDetailViewItem:NavigationItem("/issue_detail")
}