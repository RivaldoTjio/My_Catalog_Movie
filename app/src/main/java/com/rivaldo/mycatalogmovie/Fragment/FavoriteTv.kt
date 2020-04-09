package com.rivaldo.mycatalogmovie.Fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rivaldo.mycatalogmovie.Activity.Description_activity
import com.rivaldo.mycatalogmovie.Adapter.FavoriteTvAdapter
import com.rivaldo.mycatalogmovie.Items.Tv

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
class FavoriteTv : Fragment() {
    private var rv_favorite_tv: RecyclerView? = null
    private lateinit var adapter: FavoriteTvAdapter
    private var pgsBar: ProgressBar? = null
    private lateinit var favoriteHelper: FavoriteHelper
    private var list = ArrayList<Tv>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_favorite_tv, container, false)
        rv_favorite_tv = root.findViewById<View>(R.id.rv_favorite_tv) as RecyclerView
        favoriteHelper = FavoriteHelper.getInstance(activity!!.applicationContext)
        favoriteHelper.open()

        rv_favorite_tv?.layoutManager = LinearLayoutManager(activity)
        adapter = FavoriteTvAdapter()
        adapter.notifyDataSetChanged()
        rv_favorite_tv?.adapter = adapter
        loadFavoriteTv()

        adapter.setOnClickCallback(object : FavoriteTvAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Tv) {
                val descriptiontv = Intent(context, Description_activity::class.java)
                descriptiontv.putExtra(Description_activity.TYPE, "tv")
                descriptiontv.putExtra(Description_activity.EXTRA_TV, data)
                startActivity(descriptiontv)
            }

            override fun onDeleteClicked(data: Tv) {
                adapter.removeItem(data)
                val result = favoriteHelper.deleteByTitle(data.Title, "tv")
            }
        })

        return root
    }

    private fun loadFavoriteTv() {
        GlobalScope.launch(Dispatchers.Main) {
            pgsBar?.visibility = View.VISIBLE
            val deferredList = async(Dispatchers.IO){
                val cursor =  favoriteHelper.queryAll("tv")
                MappingHelper.mapCursorToArrayListTv(cursor)
            }
            val List = deferredList.await()
            if(List.size > 0 ){
                adapter.listTv = List
            } else {
                adapter.listTv = ArrayList()
            }

            adapter.getListTv(List)

        }
    }


}
