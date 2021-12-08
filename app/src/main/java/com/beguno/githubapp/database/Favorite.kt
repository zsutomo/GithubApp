package com.beguno.githubapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = 0,

    @ColumnInfo(name = "username")
    var username: String? = null,

    @ColumnInfo(name = "avatar")
    var avatar: String? = null,

    )