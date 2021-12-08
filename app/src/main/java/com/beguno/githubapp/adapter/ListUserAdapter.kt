package com.beguno.githubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beguno.githubapp.R
import com.beguno.githubapp.databinding.ItemUserLayoutBinding
import com.beguno.githubapp.model.ItemsItem
import com.bumptech.glide.Glide

class ListUserAdapter(private val listUser: List<ItemsItem>): RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            val binding = ItemUserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(onItemClickCallback, listUser[position])
    }

    override fun getItemCount() = listUser.size

    class ListViewHolder(private val lisItemBinding: ItemUserLayoutBinding): RecyclerView.ViewHolder(lisItemBinding.root) {

        fun bind(onItemClickCallback: OnItemClickCallback, userItem: ItemsItem) {
            lisItemBinding.apply {
                userItem.apply {
                    Glide.with(root)
                        .load(avatarUrl)
                        .override(60, 60)
                        .placeholder(R.drawable.ic_person)
                        .into(imgItemPhoto)

                    tvUserType.text = id.toString()
                    tvUsername.text = login

                    itemUser.setOnClickListener{ onItemClickCallback.onItemClicked(userItem)

                    }
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