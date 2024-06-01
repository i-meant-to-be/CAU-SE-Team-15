package com.cause15.issuetrackerapp.data.model

import com.cause15.issuetrackerapp.util.enums.UserType
import java.util.UUID

class User(
    private var id: UUID,
    private var name: String,
    private var password: String,
    private var userType: UserType,
    private var tags: MutableSet<String>
) {
    constructor(
        name: String,
        password: String,
        userType: UserType
    ) : this(
        UUID.randomUUID(),
        name,
        password,
        userType,
        mutableSetOf()
    )

    fun copy(
        id: UUID?,
        name: String?,
        password: String?,
        userType: UserType?,
        tags: MutableSet<String>?
    ): User {
        return User(
            id = id ?: this.id,
            name = name ?: this.name,
            password = password ?: this.password,
            userType = userType ?: this.userType,
            tags = tags ?: this.tags
        )
    }

    fun updateTag(issueTags: MutableList<String>) {
        this.tags.addAll(issueTags)
    }

    fun calculateJaccard(issue: Issue): Float {
        val unionSet = this.tags.toHashSet()
        unionSet.addAll(issue.tags.toHashSet())

        val interactionSet = this.tags.toHashSet()
        interactionSet.retainAll(issue.tags.toHashSet())

        return unionSet.size.toFloat() / interactionSet.size.toFloat()
    }
}