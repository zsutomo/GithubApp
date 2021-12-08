package com.beguno.githubapp.networking

import com.beguno.githubapp.model.DetailResponse
import com.beguno.githubapp.model.FollowersResponseItem
import com.beguno.githubapp.model.FollowingResponseItem
import com.beguno.githubapp.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users?")
    fun getUser(
        @Query("q") q: String,
        @Header("Authorization") token:String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetails(
        @Path("username") username: String,
        @Header("Authorization") token:String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
        @Header("Authorization") token:String
    ): Call<List<FollowersResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
        @Header("Authorization") token:String
    ): Call<List<FollowingResponseItem>>

}