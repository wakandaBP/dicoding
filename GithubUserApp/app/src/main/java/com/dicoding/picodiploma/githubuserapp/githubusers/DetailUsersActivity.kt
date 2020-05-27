package com.dicoding.picodiploma.githubuserapp.githubusers

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.dicoding.picodiploma.githubuserapp.R
import kotlinx.android.synthetic.main.activity_detail_users.*


class DetailUsersActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var githubAccount : String

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_users)

        val users = intent.getParcelableExtra(EXTRA_USER) as GithubUsers
        detail_tv_name.text = users.name.toString()
        detail_tv_repo.text = users.repository.toString()
        detail_tv_followers.text = users.follower.toString()
        detail_tv_following.text = users.following.toString()
        detail_company.text = users.company.toString()
        detail_place.text = users.location.toString()

        val username = "@${users.username}"
        detail_github_username.text = username
        githubAccount = users.username.toString()

        Glide.with(this)
            .load(users.avatar)
            .apply(RequestOptions().override(350,550))
            .into(this.detail_user_photo)

        Glide.with(this).load(users.avatar)
            .into(object : SimpleTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    profile_background.background = resource
                    profile_background.background.alpha = 110
                }
            })

        btn_github.setOnClickListener(this)
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
