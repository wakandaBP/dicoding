package com.dicoding.picodiploma.githubuserapp.githubusers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.dataclass.detailuser.DetailUser
import com.dicoding.picodiploma.githubuserapp.dataclass.userlist.GithubUsers
import com.dicoding.picodiploma.githubuserapp.utils.ApiService
import com.dicoding.picodiploma.githubuserapp.utils.RetroInstance
import kotlinx.android.synthetic.main.activity_detail_users.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUsersActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var githubAccount : String

    private val retrofit = RetroInstance.buildRetrofit()
    private val api = retrofit.create(ApiService::class.java)

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_users)

        val users = intent.getParcelableExtra(EXTRA_USERNAME) as GithubUsers
        loadUser(users.username.toString(),this)

        btn_github.setOnClickListener(this)
    }

    private fun loadUser(username: String, context: Context){
        api.profileUser(username).enqueue(object: Callback<DetailUser> {
            override fun onFailure(call: Call<DetailUser>, t: Throwable) {

            }

            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                when (response.code()) {
                    200 -> response.body().let {
                        detail_tv_name.text = response.body().name.toString()

                        Glide.with(context)
                            .load(response.body().profileUrl)
                            .apply(RequestOptions().override(350, 550))
                            .into(detail_user_photo)

                        }

                    408 -> Toast.makeText(context,"Request timeout", Toast.LENGTH_SHORT).show()
                }

            }

        })
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_github -> {
                val viewGithub = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/$githubAccount"))
                if (intent.resolveActivity(packageManager) != null){
                    startActivity(viewGithub)
                }
            }
        }
    }
}
