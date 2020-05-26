package com.dicoding.picodiploma.githubuserapp.githubusers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuserapp.R

class CardViewUserAdapter (private val listUsers : ArrayList<GithubUsers>) : RecyclerView.Adapter<CardViewUserAdapter.CardViewViewHolder> () {
    private lateinit var onItemClickCallback: OnItemClickCallback

    companion object {
        private lateinit var context: Context

        fun setContext(con: Context) {
            context = con
        }
    }

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imgPhoto : ImageView = itemView.findViewById(R.id.img_users_photo)
        var tvName : TextView = itemView.findViewById(R.id.txt_name)
        var tvPlace : TextView = itemView.findViewById(R.id.tv_place)
        var tvCompany : TextView = itemView.findViewById(R.id.tv_company)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
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
        val user = listUsers[position]
        val parcelUsers = GithubUsers(
            user.avatar,
            user.name,
            user.location,
            user.follower,
            user.following,
            user.username,
            user.company,
            user.repository
        )

        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .apply(RequestOptions().override(350,550))
            .into(holder.imgPhoto)

        holder.tvName.text = user.name
        holder.tvPlace.text = user.location
        holder.tvCompany.text = user.company

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUsers[holder.adapterPosition])

            val iDetailUsers = Intent(context, DetailUsersActivity::class.java)
            iDetailUsers.putExtra(DetailUsersActivity.EXTRA_USER, parcelUsers)
            context.startActivity(iDetailUsers)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUsers)
    }
}

