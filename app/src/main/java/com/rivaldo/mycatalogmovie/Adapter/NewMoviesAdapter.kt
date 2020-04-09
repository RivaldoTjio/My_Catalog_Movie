package com.rivaldo.mycatalogmovie.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldo.mycatalogmovie.Items.Movie
import com.rivaldo.mycatalogmovie.R
import kotlinx.android.synthetic.main.item_movies.view.*

class NewMoviesAdapter : RecyclerView.Adapter<NewMoviesAdapter.MoviesViewHolder>() {
    private val mData = ArrayList<Movie>()

    private var onItemClickCallback: OnItemClickCallback? = null
    private var favoritebutton: Button? = null


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<Movie>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieItems: Movie) {
            with(itemView) {
                title_movie.text = movieItems.Title
                txt_description.text = movieItems.Description
                Glide.with(this)
                    .load(movieItems.Photo)
                    .into(img_photo)
                favorite_button.setOnClickListener {
                onItemClickCallback?.onFavoriteClicked(movieItems)
                }
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(movieItems) }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return MoviesViewHolder(mView)

    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
        fun onFavoriteClicked(data: Movie)
    }


}