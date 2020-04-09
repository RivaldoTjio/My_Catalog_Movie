package com.rivaldo.mycatalogmovie.Fragment


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rivaldo.mycatalogmovie.Activity.Description_activity
import com.rivaldo.mycatalogmovie.Adapter.FavoriteMoviesAdapter
import com.rivaldo.mycatalogmovie.Adapter.NewMoviesAdapter
import com.rivaldo.mycatalogmovie.Items.Movie

import com.rivaldo.mycatalogmovie.R
import com.rivaldo.mycatalogmovie.db.FavoriteHelper
import com.rivaldo.mycatalogmovie.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMovie : Fragment() {

    private var rv_favorite_movies: RecyclerView? = null
    private lateinit var adapter: FavoriteMoviesAdapter
    private var pgsBar: ProgressBar? = null
    private lateinit var favoriteHelper: FavoriteHelper
    private var list = ArrayList<Movie>()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        rv_favorite_movies = root.findViewById<View>(R.id.rv_favorite_movie) as RecyclerView
        favoriteHelper = FavoriteHelper.getInstance(activity!!.applicationContext)
        favoriteHelper.open()

        rv_favorite_movies?.layoutManager = LinearLayoutManager(activity)
        adapter = FavoriteMoviesAdapter()
        adapter.notifyDataSetChanged()
        rv_favorite_movies?.adapter = adapter
        loadFavoriteMovie()

        adapter.setOnClickCallback(object : FavoriteMoviesAdapter.OnItemClickCallback {
            override fun onDeleteClicked(data: Movie) {
                adapter.removeItem(data)
                val result = favoriteHelper.deleteByTitle(data.Title,"movie")
                Toast.makeText(context, "Berhasil Dihapus", Toast.LENGTH_SHORT).show()
            }

            override fun onItemClicked(data: Movie) {
                val description = Intent(context, Description_activity::class.java)
                description.putExtra(Description_activity.TYPE, "movie")
                description.putExtra(Description_activity.EXTRA_MOVIE, data)
                startActivity(description)
            }
        })

        return root
    }

    private fun loadFavoriteMovie() {
        GlobalScope.launch(Dispatchers.Main) {
            pgsBar?.visibility = View.VISIBLE
            val deferredList = async(Dispatchers.IO) {
                val cursor =  favoriteHelper.queryAll("movie")
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val List = deferredList.await()
            if (List.size > 0) {
                adapter.listMovies = List
            } else {
                adapter.listMovies = ArrayList()
                // no data
            }
            //list = List
            adapter.getList(List)

        }
    }


}
