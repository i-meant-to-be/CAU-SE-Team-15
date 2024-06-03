package com.cause15.issuetrackerapp.util.enums

enum class IssueState {
    NEW, ASSIGNED, FIXED, RESOLVED, CLOSED, REOPENED;

    override fun toString(): String {
        return when (this) {
            NEW -> "New"
            ASSIGNED -> "Assigned"
            FIXED -> "Fixed"
            RESOLVED -> "Resolved"
            CLOSED -> "Closed"
            REOPENED -> "Reopened"
        }
    }
}