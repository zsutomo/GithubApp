package com.beguno.githubapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.beguno.githubapp.database.Favorite
import com.beguno.githubapp.database.FavoriteDao
import com.beguno.githubapp.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteViewModel : ViewModel() {
    private lateinit var favoriteDao : FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun init(context: Context) {
       favoriteDao = FavoriteRoomDatabase.getDatabase(context).favoriteDao()
    }

    fun insert(favorite: Favorite) {
        executorService.execute { favoriteDao.insert(favorite) }
    }

    fun delete(favorite: Favorite) {
        executorService.execute { favoriteDao.delete(favorite) }
    }

    fun getAllFavorites(): LiveData<List<Favorite>> {
        return favoriteDao.getAllFavorites()
    }

    fun getUserFavoriteById(id: Int): LiveData<List<Favorite>> {
        return favoriteDao.getUserFavoriteById(id)
    }
}