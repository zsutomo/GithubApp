package com.beguno.githubapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.beguno.githubapp.viewmodel.SplashViewModel
import com.beguno.githubapp.databinding.ActivitySplashBinding
import com.beguno.githubapp.helper.SettingPreferences
import com.beguno.githubapp.viewmodel.ThemeViewModel
import com.beguno.githubapp.viewmodel.ThemeViewModelFactory

class SplashActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private var splashBinding: ActivitySplashBinding? = null

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        changeTheme()

        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding!!.root)

        splashViewModel.liveData.observe(this, { liveData ->
            gotoMainScreen();
        })
    }

    private fun changeTheme() {
        val pref = SettingPreferences.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })
    }

    private fun gotoMainScreen() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}