package com.rivaldo.mycatalogmovie.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rivaldo.mycatalogmovie.Items.SearchedMovie
import com.rivaldo.mycatalogmovie.Items.SearchedTv
import com.rivaldo.mycatalogmovie.R

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private val mData = ArrayList<SearchedMovie>()
    private val tData = ArrayList<SearchedTv>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return ViewHolder(mView)

    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}