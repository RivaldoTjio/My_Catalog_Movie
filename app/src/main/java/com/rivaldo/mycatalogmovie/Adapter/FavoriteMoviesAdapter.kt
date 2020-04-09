package com.rivaldo.mycatalogmovie.Adapter

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldo.mycatalogmovie.Fragment.FavoriteMovie
import com.rivaldo.mycatalogmovie.Items.Movie
import com.rivaldo.mycatalogmovie.R
import com.rivaldo.mycatalogmovie.db.FavoriteHelper
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import kotlinx.android.synthetic.main.item_favorite_movie.view.*
import kotlinx.android.synthetic.main.item_movies.view.*

class FavoriteMoviesAdapter : RecyclerView.Adapter<FavoriteMoviesAdapter.MovieViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    private lateinit var favoriteHelper: FavoriteHelper
    var listMovies = ArrayList<Movie>()



    set(listMovies) {
        if (listMovies.size > 0 ) {
            this.listMovies.clear()
        }
        this.listMovies.addAll(listMovies)

        notifyDataSetChanged()
    }

    fun getList(listMovie: ArrayList<Movie>) {
        listMovies = listMovie
    }



    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addItem(movie: Movie) {
        this.listMovies.add(movie)
        notifyItemInserted(this.listMovies.size -1)
    }

    fun updateItem(position: Int, movie: Movie) {
        this.listMovies[position] = movie
        notifyItemChanged(position, movie)
    }

    fun removeItem(movie: Movie) {
        var position = this.listMovies.indexOf(movie)
        this.listMovies.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listMovies.size)
    }


    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {

            with(itemView) {
                title_fav_movie.text = movie.Title
                fav_description.text = movie.Description
                Glide.with(this)
                    .load(movie.Photo)
                    .into(imageView)

                delete_button.setOnClickListener {
                    onItemClickCallback?.onDeleteClicked(movie)
               // val result = favoriteHelper.deleteByTitle(movie.Title)
                    itemView.setOnClickListener { onItemClickCallback?.onItemClicked(movie) }
              }

                // onclick listener delete favorite

            }
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteMoviesAdapter.MovieViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_movie, parent, false )
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = this.listMovies.size

    override fun onBindViewHolder(holder: FavoriteMoviesAdapter.MovieViewHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
        fun onDeleteClicked(data: Movie)
    }


}