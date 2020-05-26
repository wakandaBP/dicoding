package com.dicoding.picodiploma.githubuserapp

import android.content.Intent
import android.content.res.TypedArray
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.githubuserapp.githubusers.CardViewUserAdapter
import com.dicoding.picodiploma.githubuserapp.githubusers.GithubUsers

class MainActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce : Boolean = false
    private lateinit var mRecyclerView : RecyclerView

    private lateinit var names : Array<String>
    private lateinit var avatar : TypedArray
    private lateinit var location : Array<String>
    private lateinit var followers : Array<String>
    private lateinit var following : Array<String>
    private lateinit var username : Array<String>
    private lateinit var company : Array<String>
    private lateinit var repository : Array<String>

    private var list = arrayListOf<GithubUsers>()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_navbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CardViewUserAdapter.setContext(this)

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
        following = resources.getStringArray(R.array.following)
        username = resources.getStringArray(R.array.username)
        company = resources.getStringArray(R.array.company)
        repository = resources.getStringArray(R.array.repository)
    }

    private fun showRecyclerCardView(){
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        val cardViewUserAdapter = CardViewUserAdapter(list)
        mRecyclerView.adapter = cardViewUserAdapter

        cardViewUserAdapter.setOnItemClickCallback(object : CardViewUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUsers) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: GithubUsers) {
        Toast.makeText(this, user.name, Toast.LENGTH_SHORT).show()
    }

    private fun addItem() {
        for (position in names.indices){
            val users = GithubUsers(avatar.getResourceId(position, -1),
                names[position],
                location[position],
                followers[position],
                following[position],
                username[position],
                company[position],
                repository[position]
            )
            list.add(users)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.go_to_github -> {
                val viewGithub = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com"))
                if (intent.resolveActivity(packageManager) != null){
                    startActivity(viewGithub)
                }
            }
        }
        return false
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true)
        }

        if (!doubleBackToExitPressedOnce) Toast.makeText(this, "Tap once more for exit", Toast.LENGTH_SHORT).show()
        doubleBackToExitPressedOnce = true
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 1500)
    }
}
