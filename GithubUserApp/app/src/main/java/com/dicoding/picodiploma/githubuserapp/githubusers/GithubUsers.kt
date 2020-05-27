package com.dicoding.picodiploma.githubuserapp.githubusers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubUsers (
    val avatar : Int?,
    val name : String?,
    val location : String?,
    val follower : String?,
    val following : String?,
    val username : String?,
    val company : String?,
    val repository : String?
) : Parcelable