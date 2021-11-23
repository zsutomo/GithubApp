package com.beguno.githubapp

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.beguno.githubapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private var splashBinding: ActivitySplashBinding? = null

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding!!.root)

        splashViewModel.liveData.observe(this, { liveData ->
            gotoMainScreen();
        })
    }

    private fun gotoMainScreen() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}