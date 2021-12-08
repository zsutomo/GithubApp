package com.beguno.githubapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beguno.githubapp.BuildConfig
import com.beguno.githubapp.model.FollowersResponseItem
import com.beguno.githubapp.networking.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {
    private val _followers = MutableLiveData<List<FollowersResponseItem>>()
    val followers: LiveData<List<FollowersResponseItem>> = _followers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun findFollowersGithub(username: String){

        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username, BuildConfig.API_KEY)
        client.enqueue(object : Callback<List<FollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowersResponseItem>>,
                response: Response<List<FollowersResponseItem>>
            ) {

                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowersResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


    companion object{
        private const val TAG = "FollowersViewModel"
    }



}