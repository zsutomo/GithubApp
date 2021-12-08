package com.beguno.githubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beguno.githubapp.R
import com.beguno.githubapp.database.Favorite
import com.beguno.githubapp.databinding.ItemUserFollowBinding
import com.bumptech.glide.Glide

class FavoriteListAdapter(private val favorite: List<Favorite>): RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder>() {
    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemUserFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorite[position], onItemClickListener)
    }

    override fun getItemCount() = favorite.size

    class FavoriteViewHolder(private val lisItemBinding: ItemUserFollowBinding): RecyclerView.ViewHolder(lisItemBinding.root) {
        fun bind(favorite: Favorite, onItemClickListener: OnItemClickListener) {
            lisItemBinding.apply {
                favorite.apply {
                    Glide.with(root)
                        .load(avatar)
                        .override(60, 60)
                        .placeholder(R.drawable.ic_person)
                        .into(imgItemPhoto)

                    tvUserType.text = id.toString()
                    tvUsername.text = username

                    root.setOnClickListener { onItemClickListener.onItemClicked(favorite) }
                }
            }
        }

    }

    interface OnItemClickListener{
        fun onItemClicked(favorite: Favorite)
    }
}