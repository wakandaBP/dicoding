package com.dicoding.picodiploma.githubuserapp.dataclass.followers

import com.google.gson.annotations.SerializedName

data class DataFollowers (
    @SerializedName("login")
    val username: String?,

    @SerializedName("url")
    val profileLink: String?
)