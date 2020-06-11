package com.dicoding.picodiploma.githubuserapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuserapp.githubusers.CardViewUserAdapter
import com.dicoding.picodiploma.githubuserapp.githubusers.ListUserViewModel
import com.dicoding.picodiploma.githubuserapp.utils.ApiService
import com.dicoding.picodiploma.githubuserapp.utils.RetroInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce : Boolean = false

    private lateinit var listUserViewModel : ListUserViewModel

    private val retrofit = RetroInstance.buildRetrofit()
    private val api = retrofit.create(ApiService::class.java)

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.searchUserBtn).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_user_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                txt_welcome.visibility = View.GONE

                listUserViewModel.setListUsers(query, api)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CardViewUserAdapter()
        adapter.notifyDataSetChanged()

        rv_users.layoutManager = LinearLayoutManager(this)
        rv_users.adapter = adapter

        listUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ListUserViewModel::class.java)

        //observer
        listUserViewModel.getListUsers().observe(this, Observer { listUsers ->
            showLoading(true)

            if (listUsers != null) {
                adapter.setData(listUsers)

                rv_users.visibility = View.VISIBLE
                showLoading(false)
            } else {
                rv_users.visibility = View.GONE

                runBlocking {
                    delay(1500L)
                    showLoading(false)

                    txt_welcome.text = getString(R.string.cant_connect)
                    txt_welcome.visibility = View.VISIBLE
                }
            }
        })
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

    fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}
