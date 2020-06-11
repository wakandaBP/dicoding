package com.dicoding.picodiploma.githubuserapp.githubusers

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubuserapp.MainActivity
import com.dicoding.picodiploma.githubuserapp.dataclass.userlist.*
import com.dicoding.picodiploma.githubuserapp.utils.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListUserViewModel(): ViewModel() {
    private val listUsers = MutableLiveData<ArrayList<GithubUsers>> ()

    fun setListUsers(params: String, api: ApiService) {

        api.searchUsers(params).enqueue(object : Callback<GithubUserList> {
            override fun onResponse(
                call: Call<GithubUserList>,
                response: Response<GithubUserList>
            ) {
                //d("test", "Response: ${response.body().items}")
                if (response.code() == 200) {
                    listUsers.postValue(response.body().items)
                }
            }

            override fun onFailure(call: Call<GithubUserList>, t: Throwable) {
                d("test", "onFailure $t")
                listUsers.postValue(null)
            }
        })

    }

    fun getListUsers() : LiveData<ArrayList<GithubUsers>> = listUsers

}