package com.dicoding.picodiploma.githubuserapp.dataclass.repositories

import com.google.gson.annotations.SerializedName

data class DataRepo (
    @SerializedName("id")
    var repoId: String?
)