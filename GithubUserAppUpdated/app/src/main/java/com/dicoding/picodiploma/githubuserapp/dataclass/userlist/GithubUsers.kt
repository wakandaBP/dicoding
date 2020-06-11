package com.dicoding.picodiploma.githubuserapp.dataclass.userlist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubUsers (
    @SerializedName("login")
    val username : String?,

    @SerializedName("avatar_url")
    var photoProfile : String?

) : Parcelable