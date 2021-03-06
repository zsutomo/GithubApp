package com.beguno.githubapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beguno.githubapp.BuildConfig
import com.beguno.githubapp.model.DetailResponse
import com.beguno.githubapp.model.ItemsItem
import com.beguno.githubapp.model.UserResponse
import com.beguno.githubapp.networking.ApiConfig
import retrofit2.*

class MainViewModel: ViewModel()  {

    private val _user = MutableLiveData<List<ItemsItem>>()
    val user: LiveData<List<ItemsItem>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findUserGithub(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username ?: "", BuildConfig.API_KEY)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()?.items
                    println("data : ${_user.value}")

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object{
        private const val TAG = "MainViewModel"
    }
}