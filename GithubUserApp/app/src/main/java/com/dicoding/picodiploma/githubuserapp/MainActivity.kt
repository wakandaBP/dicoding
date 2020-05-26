package com.dicoding.picodiploma.githubuserapp

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.githubuserapp.githubusers.CardViewUserAdapter
import com.dicoding.picodiploma.githubuserapp.githubusers.GithubUsers

class MainActivity : AppCompatActivity() {
    private lateinit var mRecyclerView : RecyclerView

    private lateinit var names : Array<String>
    private lateinit var avatar : TypedArray
    private lateinit var location : Array<String>
    private lateinit var followers : Array<String>
    private var list = arrayListOf<GithubUsers>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.rv_users)
        mRecyclerView.setHasFixedSize(true)

        prepare()
        addItem()
        showRecyclerCardView()
    }

    private fun prepare(){
        names = resources.getStringArray(R.array.name)
        avatar = resources.obtainTypedArray(R.array.avatar)
        location = resources.getStringArray(R.array.location)
        followers = resources.getStringArray(R.array.followers)
    }

    private fun showRecyclerCardView(){
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        val cardViewHeroAdapter = CardViewUserAdapter(list)
        mRecyclerView.adapter = cardViewHeroAdapter
    }

    private fun addItem() {
        for (position in names.indices){
            val users = GithubUsers(avatar.getResourceId(position, -1),
                names[position],
                location[position],
                followers[position]
            )
            list.add(users)
        }
    }

}
