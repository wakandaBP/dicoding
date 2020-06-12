package com.dicoding.picodiploma.githubuserapp.githubusers.following

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.dataclass.following.DataFollowing
import kotlinx.android.synthetic.main.item_followers_following.view.*

class FollowingCardAdapter: RecyclerView.Adapter<FollowingCardAdapter.ViewHolder> () {
    private val listFollowing = ArrayList<DataFollowing>()

    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(followingList: DataFollowing){
            with(itemView) {
                txt_follower_following.text = followingList.username

                Glide.with(itemView.context)
                    .load(followingList.photoProfile)
                    .apply(RequestOptions().override(350,550))
                    .into(img_following_followers_photo)

                itemView.setOnClickListener {
                    //show toast on user click
                    Toast.makeText(itemView.context, followingList.username, Toast.LENGTH_SHORT).show()

                    /*val iDetailUsers = Intent(itemView.context, DetailUsersActivity::class.java)
                    iDetailUsers.putExtra(DetailUsersActivity.EXTRA_USERNAME, listFollowers)
                    itemView.context.startActivity(iDetailUsers)*/
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowingCardAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_followers_following, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listFollowing.size

    override fun onBindViewHolder(holder: FollowingCardAdapter.ViewHolder, position: Int) {
        holder.bind(listFollowing[position])
    }

    fun setData(items: ArrayList<DataFollowing>) {
        listFollowing.clear()
        listFollowing.addAll(items)
        notifyDataSetChanged()
    }
}