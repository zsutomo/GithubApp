package com.beguno.githubapp.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager2.widget.ViewPager2
import com.beguno.githubapp.R
import com.beguno.githubapp.adapter.DetailSectionPagerAdapter
import com.beguno.githubapp.database.Favorite
import com.beguno.githubapp.databinding.ActivityDetailBinding
import com.beguno.githubapp.model.DetailResponse
import com.beguno.githubapp.viewmodel.DetailViewModel
import com.beguno.githubapp.viewmodel.FavoriteViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var userName: String
    private val detailViewModel by viewModels<DetailViewModel>()
    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userName = intent.getStringExtra(EXTRA_DETAIL).toString()

        favoriteViewModel.init(this)

        detailViewModel.findDetail(userName)

        detailViewModel.detail.observe(this@DetailActivity, {
            showData(it)
        })

        val sectionsPagerAdapter = DetailSectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager_detail)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tab_layout_detail)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.isLoading.observe(this@DetailActivity, {
            showLoading(it)
        })

    }

    private fun showLoading(isLoading: Boolean) {
        detailBinding.progressCircular.visibility = if (isLoading) View.VISIBLE
        else View.GONE
    }

    fun getMyData(): String {
        return userName
    }

    private fun showData(item: DetailResponse) {
        detailBinding.apply {
            item.apply {
                title = userName

                favoriteViewModel.getUserFavoriteById(id ?: 0).observe(this@DetailActivity, {
                    if (it.isNotEmpty()) {
                        favoriteIconState(true)
                    } else {
                        favoriteIconState(false)
                    }
                })

                Glide.with(this@DetailActivity)
                    .load(avatarUrl).placeholder(R.drawable.ic_person)
                    .into(imgDetailUser)

                tvNameDetail.text = name.toString()
                tvUserlogin.text = login.toString()

                tvLocationDetail.text =
                    if (location.toString() == "null") getString(R.string.location_not_found)
                    else location.toString()

                detailBinding.userInfoInclude.apply {
                    tvRepositoryDetail.text = publicRepos.toString()
                    tvFollowersDetail.text = followers.toString()
                    tvFollowingDetail.text = following.toString()
                }

                var state = false

                ivFavorite.setOnClickListener {
                    state = !state

                    setFavorite(state, Favorite(id = id, username = userName, avatar = avatarUrl))
                }
            }
        }
    }

    private fun setFavorite(state: Boolean, favorite: Favorite) {
        favoriteIconState(state)
        if (state) {
            favoriteViewModel.insert(favorite)
        } else {
            favoriteViewModel.delete(favorite)
        }
    }

    private fun favoriteIconState(state: Boolean) {
        if (state) {
            detailBinding.ivFavorite.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_red, null))
        } else {
            detailBinding.ivFavorite.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_favorite_black, null))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            val shareMessage = getString(R.string.share_message, userName)

            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, userName)
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)

            startActivity(Intent.createChooser(shareIntent, "Sharing"))
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers_title,
            R.string.following_title

        )

    }
}