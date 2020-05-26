package com.dicoding.picodiploma.githubuserapp.githubusers

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
    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imgPhoto : ImageView = itemView.findViewById(R.id.img_users_photo)
        var tvName : TextView = itemView.findViewById(R.id.txt_name)
        var tvPlace : TextView = itemView.findViewById(R.id.tv_place)
        var tvFollowers : TextView = itemView.findViewById(R.id.tv_followers)
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

        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .apply(RequestOptions().override(350,550))
            .into(holder.imgPhoto)

        holder.tvName.text = user.name
        holder.tvPlace.text = user.location
        holder.tvFollowers.text = user.follower.toString()
    }
}

