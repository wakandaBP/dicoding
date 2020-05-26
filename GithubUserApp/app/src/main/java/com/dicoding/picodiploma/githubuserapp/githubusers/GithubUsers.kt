package com.dicoding.picodiploma.githubuserapp.githubusers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubUsers (
    var avatar : Int?,
    var name : String?,
    var location : String?,
    var follower : String?,
    var following : String?,
    var username : String?,
    var company : String?,
    var repository : String?
) : Parcelable