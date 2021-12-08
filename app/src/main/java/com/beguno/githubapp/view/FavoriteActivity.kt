package com.beguno.githubapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.beguno.githubapp.adapter.FavoriteListAdapter
import com.beguno.githubapp.database.Favorite
import com.beguno.githubapp.databinding.ActivityFavoriteBinding
import com.beguno.githubapp.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity(), FavoriteListAdapter.OnItemClickListener {
    private lateinit var binding: ActivityFavoriteBinding

    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteViewModel.init(this)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)

        favoriteViewModel.getAllFavorites().observe(this, {
            val adapter = FavoriteListAdapter(it)
            binding.rvFavorite.adapter = adapter
            adapter.setOnItemClickListener(this)
        })
    }

    override fun onItemClicked(favorite: Favorite) {
        val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DETAIL, favorite.username)
        startActivity(intent)
    }
}