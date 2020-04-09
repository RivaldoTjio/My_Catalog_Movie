package com.rivaldo.mycatalogmovie.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rivaldo.mycatalogmovie.Items.Movie
import com.rivaldo.mycatalogmovie.Items.Tv
import com.rivaldo.mycatalogmovie.R

class Description_activity : AppCompatActivity() {


    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TV = "extra_tv"
        const val TYPE = "type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description_activity)

        val kind = intent.getStringExtra(TYPE)
        when (kind) {
            "movie" -> setMovies()
            "tv" -> setTv()
        }

    }

    private fun setMovies() {
        val gambar: ImageView = findViewById(R.id.imageView2)
        val judul: TextView = findViewById(R.id.Movie_title)
        val desc: TextView = findViewById(R.id.full_desc)
        val movie = intent.getParcelableExtra(EXTRA_MOVIE) as Movie
        Glide.with(this)
            .load(movie.Photo)
            .into(gambar)
        judul.text = movie.Title
        desc.text = movie.Description
    }

    private fun setTv() {
        val gambar: ImageView = findViewById(R.id.imageView2)
        val judul: TextView = findViewById(R.id.Movie_title)
        val desc: TextView = findViewById(R.id.full_desc)
        val tv_show = intent.getParcelableExtra<Tv>(EXTRA_TV)
        Glide.with(this)
            .load(tv_show.Photo)
            .into(gambar)
        judul.text = tv_show.Title
        desc.text = tv_show.Description
    }
}
