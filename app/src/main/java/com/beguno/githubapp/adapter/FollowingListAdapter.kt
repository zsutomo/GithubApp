package com.beguno.githubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.beguno.githubapp.R
import com.beguno.githubapp.databinding.ItemUserFollowBinding
import com.beguno.githubapp.model.FollowingResponseItem
import com.bumptech.glide.Glide

class FollowingListAdapter(private val listFollowing: List<FollowingResponseItem>): RecyclerView.Adapter<FollowingListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollowing[position])
    }

    override fun getItemCount() = listFollowing.size

    class ListViewHolder(private val lisItemBinding: ItemUserFollowBinding): RecyclerView.ViewHolder(lisItemBinding.root) {
        fun bind(listFollowing: FollowingResponseItem) {
            lisItemBinding.apply {
                listFollowing.apply {
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
}