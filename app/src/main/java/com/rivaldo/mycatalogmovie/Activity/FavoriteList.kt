package com.rivaldo.mycatalogmovie.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rivaldo.mycatalogmovie.FavoriteSectionsPagerAdapter
import com.rivaldo.mycatalogmovie.R
import kotlinx.android.synthetic.main.activity_favorite_list.*

class FavoriteList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)
        val favoriteSectionsPagerAdapter =
            FavoriteSectionsPagerAdapter(this, supportFragmentManager)
        view_pager_favorite.adapter = favoriteSectionsPagerAdapter
        tabs_favorite.setupWithViewPager(view_pager_favorite)
        supportActionBar?.elevation = 0f
        title = "Favorite List"
    }
}
