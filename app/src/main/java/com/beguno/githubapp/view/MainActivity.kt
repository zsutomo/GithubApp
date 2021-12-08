package com.beguno.githubapp.view

import android.app.Dialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.beguno.githubapp.R
import com.beguno.githubapp.adapter.ListUserAdapter
import com.beguno.githubapp.databinding.ActivityMainBinding
import com.beguno.githubapp.helper.SettingPreferences
import com.beguno.githubapp.model.ItemsItem
import com.beguno.githubapp.viewmodel.MainViewModel
import com.beguno.githubapp.viewmodel.ThemeViewModel
import com.beguno.githubapp.viewmodel.ThemeViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var themedialog: Dialog
    private lateinit var mainBinding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainViewModel.findUserGithub("zak")

        mainViewModel.user.observe(this@MainActivity, { items ->
            showRecyclerList(items)
        })

        mainViewModel.isLoading.observe(this@MainActivity, {
            showLoading(it)
        })

//        changeThemes()
    }

    private fun showLoading(isLoading: Boolean) {
        mainBinding.progressHorizontal.visibility = if (isLoading) View.VISIBLE
        else View.GONE
    }

    private fun showRecyclerList(items: List<ItemsItem>) {
        mainBinding.tvDataIsempty.visibility = if (items.isEmpty()) View.VISIBLE else View.INVISIBLE
        mainBinding.rvUserGit.apply {
            layoutManager =
                if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(baseContext, 2)
                } else {
                    LinearLayoutManager(baseContext)
                }

            val listUserAdapter = ListUserAdapter(items)
            println("test data : ${listUserAdapter.itemCount}")

            setHasFixedSize(true)
            adapter = listUserAdapter
            listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ItemsItem) {
                    showSelectedUser(data)
                }
            })
        }
    }

    private fun showSelectedUser(data: ItemsItem) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DETAIL, data.login)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                if (query.isEmpty()) {
                    showLoading(false)

                } else {
                    query.let {
                        showLoading(true)
                        mainViewModel.findUserGithub(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when(id) {
            R.id.theme -> showThemes()
            R.id.favorite -> startActivity(Intent(this, FavoriteActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showThemes() {
        changeThemes()
        themedialog.show()
    }

    private fun changeThemes() {
        themedialog = Dialog(this)
        themedialog.setContentView(R.layout.dialog_theme)
        val switchTheme = themedialog.findViewById<SwitchMaterial>(R.id.switch_Theme)

        val pref = SettingPreferences.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            })

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }



}