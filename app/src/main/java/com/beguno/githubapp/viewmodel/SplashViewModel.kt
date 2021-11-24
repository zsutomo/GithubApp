package com.beguno.githubapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel:ViewModel() {
    val liveData: LiveData<SplashState>
    get() = mutableLiveData
    private val mutableLiveData = MutableLiveData<SplashState>()

    init {
        GlobalScope.launch {
            delay(5000)
            mutableLiveData.postValue(SplashState.SplashActivity)
        }
    }

    sealed class SplashState{
        object SplashActivity: SplashState()
    }
}