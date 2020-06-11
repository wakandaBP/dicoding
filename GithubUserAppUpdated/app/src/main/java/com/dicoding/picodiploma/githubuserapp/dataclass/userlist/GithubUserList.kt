package com.dicoding.picodiploma.githubuserapp.dataclass.userlist

import com.google.gson.annotations.SerializedName

data class GithubUserList (
    @SerializedName("items")
    val items: ArrayList<GithubUsers>
)