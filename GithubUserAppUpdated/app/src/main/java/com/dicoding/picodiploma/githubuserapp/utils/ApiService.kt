package com.dicoding.picodiploma.githubuserapp.utils

import com.dicoding.picodiploma.githubuserapp.dataclass.detailuser.DetailUser
import com.dicoding.picodiploma.githubuserapp.dataclass.userlist.*
import com.dicoding.picodiploma.githubuserapp.dataclass.followers.*
import com.dicoding.picodiploma.githubuserapp.dataclass.following.*
import com.dicoding.picodiploma.githubuserapp.dataclass.repositories.DataRepo
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //@Headers("Authorization: token 9694c979924cd27574f1fdd31f578cf1ae23dbac")

    @GET("/search/users")
    fun searchUsers(@Query("q") str: String?): Call<GithubUserList>

    @GET("/users/{username}")
    fun profileUser(@Path("username") username: String?): Call<DetailUser>

    @GET("/users/{username}/followers")
    fun followersUser(@Path("username") username: String?): Call<List<DataFollowers>>

    @GET("/users/{username}/following")
    fun followingUser(@Path("username") username: String?): Call<List<DataFollowing>>

    @GET("/users/{username}/repos")
    fun getRepoUser(@Path("username") username: String?): Call<List<DataRepo>>
}