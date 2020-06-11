package com.dicoding.picodiploma.githubuserapp.dataclass.following

import com.google.gson.annotations.SerializedName

data class DataFollowing (
    @SerializedName("login")
    val username: String?,

    @SerializedName("url")
    val profileLink: String?
)