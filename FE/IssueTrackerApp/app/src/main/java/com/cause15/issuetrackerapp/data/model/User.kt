package com.cause15.issuetrackerapp.data.model

import android.os.Parcelable
import com.cause15.issuetrackerapp.util.enums.UserType
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class User(
    @SerializedName("id") private var id: UUID,
    @SerializedName("name") private var name: String,
    @SerializedName("password") private var password: String,
    @SerializedName("type") private var userType: UserType,
    @SerializedName("tags") private var tags: MutableSet<String>
) : Parcelable {
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