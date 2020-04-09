package com.rivaldo.mycatalogmovie.Fragment

import android.app.SearchManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rivaldo.mycatalogmovie.Activity.Description_activity
import com.rivaldo.mycatalogmovie.Adapter.NewMoviesAdapter
import com.rivaldo.mycatalogmovie.Items.Movie
import com.rivaldo.mycatalogmovie.MainViewModel.MainViewModel
import com.rivaldo.mycatalogmovie.R
import com.rivaldo.mycatalogmovie.db.DatabaseContract
import com.rivaldo.mycatalogmovie.db.FavoriteHelper
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment()  {
    private lateinit var adapter: NewMoviesAdapter

    private var rv_movie: RecyclerView? = null
    private lateinit var mainViewModel: MainViewModel
    private var pgsBar: ProgressBar? = null
    private val locale: String = Locale.getDefault().toLanguageTag()
    private var addToFavorite: Button? = null
    private lateinit var favoriteHelper: FavoriteHelper
    private var searchmode: Boolean = false
    private var itemsearch: String? = null
    private var releaseMode: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_movie, container, false)
       // val dbHelper = MyDatabaseHelper(context!!)
       // val db = dbHelper.writableDatabase

        setHasOptionsMenu(true)
        pgsBar = root.findViewById(R.id.progressBarmovie) as ProgressBar
        addToFavorite = root.findViewById(R.id.action_favorite_list)
        rv_movie = root.findViewById<View>(R.id.rv_movies) as RecyclerView
        adapter = NewMoviesAdapter()
        adapter.notifyDataSetChanged()

        rv_movie?.layoutManager = LinearLayoutManager(activity)
        rv_movie?.adapter = adapter

            mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                MainViewModel::class.java
            )
            showLoading(true)
            favoriteHelper = FavoriteHelper.getInstance(activity!!.applicationContext)
            favoriteHelper.open()


            mainViewModel.getListMovies(locale)
            mainViewModel.getMovies().observe(this, Observer { movieItems ->
                if (movieItems != null) {
                    adapter.setData(movieItems)
                    showLoading(false)
                }
            })
            adapter.setOnItemClickCallback(object : NewMoviesAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Movie) {
                    val description = Intent(context, Description_activity::class.java)
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

//                val newRowId = db?.insert(Movie.TABLE_MOVIE, null, values )


                }
            })

            return root



//
//            mainViewModel.searchMovie(itemsearch,locale)
//            mainViewModel.getFounded().observe(this, Observer { Founded ->
//                if (Founded != null) {
//                    adapter.setData(Founded)
//                    showLoading(false)
//                }
//            })



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var searchView = menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                mainViewModel.searchMovie(query,locale)
                mainViewModel.getFounded().observe(this@MovieFragment, Observer {Founded ->
                    if (Founded != null) {
                        adapter.setData(Founded)
                        adapter.notifyDataSetChanged()
                    }
                })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mainViewModel.searchMovie(newText,locale)
                mainViewModel.getFounded().observe(this@MovieFragment, Observer {Founded ->
                    if (Founded != null) {
                        adapter.setData(Founded)
                        adapter.notifyDataSetChanged()
                    }
                })

                return false
            }


        })



    }



    fun setMode(mode: Boolean, item:String?) {
        if (mode==true) {
            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
            searchmode = mode
            itemsearch = item
            mainViewModel.searchMovie(itemsearch,locale)
            mainViewModel.getFounded().observe(this, Observer { Founded ->
                if (Founded != null) {
                    adapter.setData(Founded)
                    adapter.notifyDataSetChanged()
                    fragmentManager?.beginTransaction()
                        ?.detach(this)
                        ?.attach(this)
                        ?.commit()

                    showLoading(false)
                }
            })

        } else {
            searchmode = false

        }
    }




    private fun showLoading(state: Boolean) {
        if (state) {
            pgsBar?.visibility = View.VISIBLE
        } else {
            pgsBar?.visibility = View.GONE
        }
    }





}