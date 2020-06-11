package com.dicoding.picodiploma.githubuserapp.githubusers

import android.content.Intent
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.dataclass.followers.DataFollowers
import com.dicoding.picodiploma.githubuserapp.dataclass.repositories.DataRepo
import com.dicoding.picodiploma.githubuserapp.dataclass.userlist.GithubUsers
import com.dicoding.picodiploma.githubuserapp.utils.ApiService
import com.dicoding.picodiploma.githubuserapp.utils.RetroInstance
import kotlinx.android.synthetic.main.item_github_users.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardViewUserAdapter : RecyclerView.Adapter<CardViewUserAdapter.CardViewViewHolder> () {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listUsers = ArrayList<GithubUsers>()

    private val retrofit = RetroInstance.buildRetrofit()
    private val api = retrofit.create(ApiService::class.java)

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(userItems: GithubUsers) {
            with(itemView) {
                txt_name.text = userItems.username

                Glide.with(itemView.context)
                    .load(userItems.photoProfile)
                    .apply(RequestOptions().override(350,550))
                    .into(img_users_photo)

                //get number of repositories
                api.getRepoUser(userItems.username).enqueue(object : Callback<List<DataRepo>> {
                    override fun onFailure(call: Call<List<DataRepo>>, t: Throwable) {
                        //d("load repo", "onFailure $t")
                    }

                    override fun onResponse(call: Call<List<DataRepo>>, response: Response<List<DataRepo>>) {
                        //d("load repo", "Number of repositories: ${response.errorBody()}")
                        if (response.code() == 200){
                            response.body()?.let {
                                tv_number_of_repos.text = response.body().size.toString()
                            }
                        } else tv_number_of_repos.text = "0"

                    }
                })

                //get number of followers
                api.followersUser(userItems.username).enqueue(object : Callback<List<DataFollowers>> {
                    override fun onFailure(call: Call<List<DataFollowers>>, t: Throwable) {
                        //d("load followers", "onFailure $t")
                    }

                    override fun onResponse(
                        call: Call<List<DataFollowers>>,
                        response: Response<List<DataFollowers>>
                    ) {
                        if (response.code() == 200){
                            response.body()?.let {
                                tv_number_of_followers.text = response.body().size.toString()
                            }
                        } else tv_number_of_followers.text = "0"
                    }
                })
            }

            //action on recycler click
            itemView.setOnClickListener {
                //show toast on user click
                Toast.makeText(itemView.context, userItems.username, Toast.LENGTH_SHORT).show()

                val iDetailUsers = Intent(itemView.context, DetailUsersActivity::class.java)
                iDetailUsers.putExtra(DetailUsersActivity.EXTRA_USERNAME, userItems)
                itemView.context.startActivity(iDetailUsers)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewUserAdapter.CardViewViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_github_users, parent, false)
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int = listUsers.size

    override fun onBindViewHolder(holder: CardViewUserAdapter.CardViewViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }

    fun setData(items: ArrayList<GithubUsers>) {
        listUsers.clear()
        listUsers.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUsers)
    }
}

