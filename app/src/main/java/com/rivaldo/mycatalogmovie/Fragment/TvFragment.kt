package com.rivaldo.mycatalogmovie.Fragment

import android.app.SearchManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rivaldo.mycatalogmovie.Adapter.NewTvAdapter
import com.rivaldo.mycatalogmovie.Activity.Description_activity
import com.rivaldo.mycatalogmovie.Items.Tv
import com.rivaldo.mycatalogmovie.MainViewModel.MainViewModelTv
import com.rivaldo.mycatalogmovie.R
import com.rivaldo.mycatalogmovie.db.DatabaseContract
import com.rivaldo.mycatalogmovie.db.FavoriteHelper
import java.util.*

/**
 * A simple [Fragment] subclass.
 */

class TvFragment : Fragment() {
    private lateinit var adapter: NewTvAdapter
    private var rv_tv: RecyclerView? = null
    private lateinit var mainViewModelTv: MainViewModelTv
    private var pgsBar: ProgressBar? = null
    private val locale: String = Locale.getDefault().toLanguageTag()
    private lateinit var favoriteHelper: FavoriteHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_tv, container, false)
        setHasOptionsMenu(true)
        pgsBar = root.findViewById(R.id.progressBarTv) as ProgressBar
        rv_tv = root.findViewById<View>(R.id.rv_tv) as RecyclerView
        adapter = NewTvAdapter()
        adapter.notifyDataSetChanged()
        rv_tv?.layoutManager = LinearLayoutManager(activity)
        rv_tv?.adapter = adapter
        mainViewModelTv = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModelTv::class.java
        )
        showLoading(true)
        favoriteHelper = FavoriteHelper.getInstance(activity!!.applicationContext)
        favoriteHelper.open()
        mainViewModelTv.getListTv(locale)



        mainViewModelTv.getTv().observe(this, Observer { tvItems ->
            if (tvItems != null) {
                adapter.setData(tvItems)
                showLoading(false)
                adapter.setOnItemClickCallback(object : NewTvAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: Tv) {
                        val descriptiontv = Intent(context, Description_activity::class.java)
                        descriptiontv.putExtra(Description_activity.TYPE, "tv")
                        descriptiontv.putExtra(Description_activity.EXTRA_TV, data)
                        startActivity(descriptiontv)
                    }

                    override fun onFavoriteClicked(data: Tv) {
                        val values = ContentValues().apply {
                            put(DatabaseContract.FavoriteTv._ID, data.id)
                            put(DatabaseContract.FavoriteTv.TITLE, data.Title)
                            put(DatabaseContract.FavoriteTv.DESCRIPTION, data.Description)
                            put(DatabaseContract.FavoriteTv.PHOTO, data.Photo)
                        }
                        val result = favoriteHelper.insert(values, "tv")

                    }
                })
            }
        })

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var searchView = menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                mainViewModelTv.searchTv(query,locale)
                mainViewModelTv.getFounded().observe(this@TvFragment, Observer { Founded ->
                    if (Founded != null) {
                        adapter.setData(Founded)
                        adapter.notifyDataSetChanged()
                    }
                })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mainViewModelTv.searchTv(newText,locale)
                mainViewModelTv.getFounded().observe(this@TvFragment, Observer {Founded ->
                    if (Founded != null) {
                        adapter.setData(Founded)
                        adapter.notifyDataSetChanged()
                    }
                })
                return false
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