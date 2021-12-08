package com.beguno.githubapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.beguno.githubapp.R
import com.beguno.githubapp.databinding.ItemUserFollowBinding
import com.beguno.githubapp.model.FollowersResponseItem
import com.beguno.githubapp.model.ItemsItem
import com.bumptech.glide.Glide

class FollowersListAdapter(private val listFollow: List<FollowersResponseItem>): RecyclerView.Adapter<FollowersListAdapter.ListFollowersViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListFollowersViewHolder {
        val binding = ItemUserFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListFollowersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListFollowersViewHolder, position: Int) {
        holder.bind(listFollow[position])
    }

    override fun getItemCount() = listFollow.size



    class ListFollowersViewHolder(private val lisItemBinding: ItemUserFollowBinding): RecyclerView.ViewHolder(lisItemBinding.root) {

        fun bind(listFollowers: FollowersResponseItem) {
            lisItemBinding.apply {
                listFollowers.apply {
                    Glide.with(root)
                        .load(avatarUrl)
                        .override(60, 60)
                        .placeholder(R.drawable.ic_person)
                        .into(imgItemPhoto)

                    tvUserType.text = id.toString()
                    tvUsername.text = login
                }
            }


        }

    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

}