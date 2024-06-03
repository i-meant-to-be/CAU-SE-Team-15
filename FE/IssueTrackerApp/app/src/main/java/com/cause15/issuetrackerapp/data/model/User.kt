package com.cause15.issuetrackerapp.data.model

import android.os.Parcelable
import com.cause15.issuetrackerapp.util.enums.UserType
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class User(
    @SerializedName("id") var id: UUID,
    @SerializedName("name") var name: String,
    @SerializedName("password") var password: String,
    @SerializedName("type") var userType: UserType,
    @SerializedName("tags") var tags: MutableSet<String>
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

    constructor() : this(
        UUID.randomUUID(),
        "",
        "",
        UserType.USER,
        mutableSetOf()
    )
}