package com.cause15.issuetrackerapp.util.enums

enum class IssueType {
    BLOCKER, CRITICAL, MAJOR, MINOR, TRIVIAL;

    override fun toString(): String {
        return when (this) {
            BLOCKER -> "Blocker"
            CRITICAL -> "Critical"
            MAJOR -> "Major"
            MINOR -> "Minor"
            TRIVIAL -> "Trivial"
        }
    }
}