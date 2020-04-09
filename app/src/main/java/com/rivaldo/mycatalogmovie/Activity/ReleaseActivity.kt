package com.rivaldo.mycatalogmovie.Activity

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rivaldo.mycatalogmovie.Adapter.NewMoviesAdapter
import com.rivaldo.mycatalogmovie.Items.Movie
import com.rivaldo.mycatalogmovie.MainViewModel.MainViewModel
import com.rivaldo.mycatalogmovie.R
import com.rivaldo.mycatalogmovie.db.DatabaseContract
import com.rivaldo.mycatalogmovie.db.FavoriteHelper

class ReleaseActivity : AppCompatActivity() {
    private lateinit var adapter: NewMoviesAdapter

    private var rvRelease: RecyclerView? = null
    private lateinit var mainViewModel: MainViewModel
    private var pgsBar: ProgressBar? = null
    private var addToFavorite: Button? = null
    private lateinit var favoriteHelper: FavoriteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_release)
        pgsBar = findViewById(R.id.progressBarRelease)
        addToFavorite = findViewById(R.id.action_favorite_list)
        rvRelease = findViewById<RecyclerView>(R.id.release_rv)
        adapter = NewMoviesAdapter()
        adapter.notifyDataSetChanged()
        rvRelease?.layoutManager = LinearLayoutManager(this)
        rvRelease?.adapter = adapter

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java
        )
        showLoading(true)
        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mainViewModel.getRelease()
            mainViewModel.getMovies().observe(this, Observer { movieItems ->
                if (movieItems != null) {
                    adapter.setData(movieItems)
                    showLoading(false)
                }
            })
        }
        adapter.setOnItemClickCallback(object : NewMoviesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                val description = Intent(this@ReleaseActivity, Description_activity::class.java)
                description.putExtra(Description_activity.TYPE, "movie")
                description.putExtra(Description_activity.EXTRA_MOVIE, data)
                startActivity(description)
            }

            override fun onFavoriteClicked(data: Movie) {
                val values = ContentValues().apply {
                    put(DatabaseContract.FavoriteColumns._ID, data.id)
                    put(DatabaseContract.FavoriteColumns.TITLE, data.Title)
                    put(DatabaseContract.FavoriteColumns.DESCRIPTION, data.Description)
                    put(DatabaseContract.FavoriteColumns.PHOTO, data.Photo)
                }
                val result = favoriteHelper.insert(values, "movie")
            }


            })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pgsBar?.visibility = View.VISIBLE
        } else {
            pgsBar?.visibility = View.GONE
        }
    }
}
